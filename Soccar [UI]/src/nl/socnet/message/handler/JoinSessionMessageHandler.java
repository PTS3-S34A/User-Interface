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
import nl.soccar.ui.Main;
import nl.soccar.ui.fx.FXMLConstants;
import nl.soccar.ui.rmi.ClientController;
import nl.socnet.message.JoinSessionMessage;
import nl.socnet.message.JoinSessionMessage.Status;

/**
 *
 * @author PTS34A
 */
public final class JoinSessionMessageHandler extends MessageHandler<JoinSessionMessage> {

    @Override
    protected void handle(Connection connection, JoinSessionMessage message) throws Exception {
        Status status = message.getStatus();
        if (status != Status.SUCCESS) {
            return;
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
        Status status = Status.valueOf(ByteBufUtilities.readString(buf));
        if (status != Status.SUCCESS) {
            return new JoinSessionMessage(status);
        }

        String roomName = ByteBufUtilities.readString(buf);
        int capacity = buf.readByte();
        MapType mapType = MapType.valueOf(ByteBufUtilities.readString(buf));
        BallType ballType = BallType.valueOf(ByteBufUtilities.readString(buf));
        Duration duration = Duration.valueOf(ByteBufUtilities.readString(buf));

        GameSettings settings = new GameSettings();
        settings.setBallType(ballType);
        settings.setMapType(mapType);
        settings.setDuration(duration);
  
        return new JoinSessionMessage(status, roomName, capacity, settings);
    }

}
