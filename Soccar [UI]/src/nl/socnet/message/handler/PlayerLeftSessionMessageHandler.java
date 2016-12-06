package nl.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import java.util.Optional;
import nl.soccar.gamecommuncation.util.ByteBufUtilities;
import nl.soccar.library.Player;
import nl.soccar.library.Room;
import nl.soccar.library.Session;
import nl.soccar.library.Team;
import nl.soccar.library.enumeration.TeamColour;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;
import nl.soccar.ui.rmi.ClientController;
import nl.socnet.message.PlayerLeftSessionMessage;

/**
 *
 * @author PTS34A
 */
public final class PlayerLeftSessionMessageHandler extends MessageHandler<PlayerLeftSessionMessage> {

    @Override
    protected void handle(Connection connection, PlayerLeftSessionMessage message) throws Exception {
        Session currentSession = ClientController.getInstance().getCurrentPlayer().getCurrentSession();

        Room room = currentSession.getRoom();
        Team team = message.getTeamColour() == TeamColour.BLUE ? room.getTeamBlue() : room.getTeamRed();

        Optional<Player> optional = team.getPlayers().stream().filter(p -> p.getUsername().equals(message.getUsername())).findFirst();
        if (!optional.isPresent()) {
            return;
        }
        
        team.leave(optional.get());
    }

    @Override
    protected void encode(Connection connection, PlayerLeftSessionMessage message, ByteBuf buf) throws Exception {
        throw new UnsupportedOperationException("Encoding not supported for Client.");
    }

    @Override
    protected PlayerLeftSessionMessage decode(Connection connection, ByteBuf buf) throws Exception {
        String username = ByteBufUtilities.readString(buf);
        TeamColour colour = TeamColour.valueOf(ByteBufUtilities.readString(buf));

        return new PlayerLeftSessionMessage(username, colour);
    }

}
