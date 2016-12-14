package nl.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import javafx.application.Platform;
import nl.soccar.gamecommuncation.util.ByteBufUtilities;
import nl.soccar.library.Player;
import nl.soccar.library.Room;
import nl.soccar.library.Session;
import nl.soccar.library.Team;
import nl.soccar.library.enumeration.CarType;
import nl.soccar.library.enumeration.Privilege;
import nl.soccar.library.enumeration.TeamColour;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;
import nl.soccar.ui.Main;
import nl.soccar.ui.fx.controller.SessionViewFXMLController;
import nl.soccar.ui.rmi.ClientController;
import nl.socnet.message.PlayerJoinedSessionMessage;

/**
 *
 * @author PTS34A
 */
public final class PlayerJoinedSessionMessageHandler extends MessageHandler<PlayerJoinedSessionMessage> {

    @Override
    protected void handle(Connection connection, PlayerJoinedSessionMessage message) throws Exception {
        Session currentSession = ClientController.getInstance().getCurrentPlayer().getCurrentSession();

        Player newPlayer = message.getPlayer();
        newPlayer.setCurrentSession(currentSession);

        Room room = currentSession.getRoom();
        Team team = message.getTeamColour() == TeamColour.BLUE ? room.getTeamBlue() : room.getTeamRed();
        team.join(newPlayer);

        Object controller = Main.getInstance().getController();
        if (controller != null && controller instanceof SessionViewFXMLController) {
            SessionViewFXMLController view = (SessionViewFXMLController) controller;
            Platform.runLater(view::setRoomInfo);
        }
    }

    @Override
    protected void encode(Connection connection, PlayerJoinedSessionMessage message, ByteBuf buf) throws Exception {
        throw new UnsupportedOperationException("Encoding not supported for Client.");
    }

    @Override
    protected PlayerJoinedSessionMessage decode(Connection connection, ByteBuf buf) throws Exception {
        String username = ByteBufUtilities.readString(buf);
        if (username == null) {
            return null;
        }

        if (buf.readableBytes() < 3) {
            buf.resetReaderIndex();
            return null;
        }

        Privilege privilege = Privilege.parse(buf.readByte());
        CarType type = CarType.parse(buf.readByte());
        TeamColour colour = TeamColour.parse(buf.readByte());

        return new PlayerJoinedSessionMessage(new Player(username, privilege, type), colour);
    }

}
