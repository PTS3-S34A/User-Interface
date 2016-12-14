package nl.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import java.util.Optional;
import javafx.application.Platform;
import nl.soccar.gamecommuncation.util.ByteBufUtilities;
import nl.soccar.library.Player;
import nl.soccar.library.Room;
import nl.soccar.library.Team;
import nl.soccar.library.enumeration.TeamColour;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;
import nl.soccar.ui.Main;
import nl.soccar.ui.fx.controller.SessionViewFXMLController;
import nl.soccar.ui.rmi.ClientController;
import nl.socnet.message.SwitchTeamMessage;

/**
 *
 * @author PTS34A
 */
public final class SwitchTeamMessageHandler extends MessageHandler<SwitchTeamMessage> {

    @Override
    protected void handle(Connection connection, SwitchTeamMessage message) throws Exception {
        Room room = ClientController.getInstance().getCurrentPlayer().getCurrentSession().getRoom();
        Team oldTeam = message.getTeamColour() == TeamColour.BLUE ? room.getTeamRed() : room.getTeamBlue();
        Team newTeam = oldTeam.getTeamColour() == TeamColour.BLUE ? room.getTeamRed() : room.getTeamBlue();

        Optional<Player> player = room.getAllPlayers().stream().filter(p -> p.getUsername().equals(message.getUsername())).findFirst();

        if (!player.isPresent()) {
            return;
        }

        oldTeam.leave(player.get());
        newTeam.join(player.get());

        Object controller = Main.getInstance().getController();
        if (controller != null && controller instanceof SessionViewFXMLController) {
            SessionViewFXMLController view = (SessionViewFXMLController) controller;
            Platform.runLater(view::setRoomInfo);
        }
    }

    @Override
    protected void encode(Connection connection, SwitchTeamMessage message, ByteBuf buf) throws Exception {
    }

    @Override
    protected SwitchTeamMessage decode(Connection connection, ByteBuf buf) throws Exception {
        String username = ByteBufUtilities.readString(buf);

        if (username == null) {
            return null;
        }

        if (buf.readableBytes() < 1) {
            buf.resetReaderIndex();
            return null;
        }

        TeamColour team = TeamColour.parse(buf.readByte());

        return new SwitchTeamMessage(username, team);
    }

}
