package nl.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import javafx.application.Platform;
import nl.soccar.gamecommuncation.util.ByteBufUtilities;
import nl.soccar.library.GameSettings;
import nl.soccar.library.Session;
import nl.soccar.library.enumeration.BallType;
import nl.soccar.library.enumeration.Duration;
import nl.soccar.library.enumeration.MapType;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;
import nl.soccar.ui.DisplayUtilities;
import nl.soccar.ui.Main;
import nl.soccar.ui.fx.FXMLConstants;
import nl.soccar.ui.rmi.ClientController;
import nl.socnet.message.JoinSessionMessage;
import nl.socnet.message.JoinSessionMessage.Status;

/**
 * Handles JoinSession messages, after the currentPlayer has joined a session.
 *
 * @author PTS34A
 */
public final class JoinSessionMessageHandler extends MessageHandler<JoinSessionMessage> {

    @Override
    protected void handle(Connection connection, JoinSessionMessage message) throws Exception {
        Status status = message.getStatus();
        switch (status) {
            case CAPACITY_OVERFLOW:
                failedJoiningOfSession("Capacity overflow", "The session is already full, wait till there is a spot free in the Room.");
                return;
            case INVALID_PASSWORD:
                failedJoiningOfSession("Invalid password", "The password you entered is incorrect.");
                return;
            case SESSION_NON_EXISTENT:
                failedJoiningOfSession("Session non exists", "The selected session doesnt exist anymore.");
                return;
            case USERNAME_EXISTS:
                failedJoiningOfSession("Username exists", "There is already a player with the same name in the Room, please change your username.");
                return;
            default:
                break;
        }

        GameSettings givenSettings = message.getGameSettings();

        Session session = new Session(message.getRoomName(), message.getPassword());
        session.getRoom().setCapacity(message.getCapacity());

        GameSettings settings = session.getGame().getGameSettings();
        settings.setDuration(givenSettings.getDuration());
        settings.setMapType(givenSettings.getMapType());
        settings.setBallType(givenSettings.getBallType());

        ClientController.getInstance().getCurrentPlayer().setCurrentSession(session);
        Platform.runLater(() -> Main.getInstance().setScene(FXMLConstants.LOCATION_SESSION_VIEW));
    }

    @Override
    protected void encode(Connection connection, JoinSessionMessage message, ByteBuf buf) throws Exception {
        ByteBufUtilities.writeString(message.getRoomName(), buf);
        ByteBufUtilities.writeString(message.getPassword(), buf);
    }

    @Override
    protected JoinSessionMessage decode(Connection connection, ByteBuf buf) throws Exception {
        String statStr = ByteBufUtilities.readString(buf);
        if (statStr == null) {
            return null;
        }

        Status status = Status.valueOf(statStr);
        if (status != Status.SUCCESS) {
            return new JoinSessionMessage(status);
        }

        String roomName = ByteBufUtilities.readString(buf);
        if (roomName == null) {
            return null;
        }

        if (buf.readableBytes() < 4) {
            buf.resetReaderIndex();
            return null;
        }

        int capacity = buf.readByte();
        MapType mapType = MapType.parse(buf.readByte());
        BallType ballType = BallType.parse(buf.readByte());
        Duration duration = Duration.parse(buf.readByte());

        GameSettings settings = new GameSettings();
        settings.setBallType(ballType);
        settings.setMapType(mapType);
        settings.setDuration(duration);

        return new JoinSessionMessage(status, roomName, capacity, settings);
    }

    private void failedJoiningOfSession(String title, String message) {
        Platform.runLater(() -> DisplayUtilities.showAlert(title, message));

        ClientController.getInstance().getClient().disconnect();
        ClientController.getInstance().setCurrentConnection(null);
    }

}
