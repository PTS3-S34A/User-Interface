package nl.socnet.message;

import nl.soccar.library.Player;
import nl.soccar.library.enumeration.TeamColour;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;
import nl.socnet.message.handler.PlayerJoinedSessionMessageHandler;

/**
 *
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.PLAYER_JOINED_SESSION_MESSAGE_ID, handler = PlayerJoinedSessionMessageHandler.class)
public final class PlayerJoinedSessionMessage extends Message {
    
    
    private final Player player;
    private final TeamColour colour;
    
    public PlayerJoinedSessionMessage(Player player, TeamColour colour) {
        this.player = player;
        this.colour = colour;
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public TeamColour getTeamColour() {
        return colour;
    }

    @Override
    public int getId() {
        return MessageConstants.PLAYER_JOINED_SESSION_MESSAGE_ID;
    }
    
}
