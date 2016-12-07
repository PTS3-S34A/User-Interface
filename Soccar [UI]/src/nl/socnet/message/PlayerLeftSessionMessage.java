package nl.socnet.message;

import nl.soccar.library.enumeration.TeamColour;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;
import nl.socnet.message.handler.PlayerLeftSessionMessageHandler;

/**
 *
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.PLAYER_LEFT_SESSION_MESSAGE_ID, handler = PlayerLeftSessionMessageHandler.class)
public final class PlayerLeftSessionMessage extends Message {
        
    private final String username;
    private final TeamColour colour;
    
    public PlayerLeftSessionMessage(String username, TeamColour colour) {
        this.username = username;
        this.colour = colour;
    }
    
    public String getUsername() {
        return username;
    }
    
    public TeamColour getTeamColour() {
        return colour;
    }

    @Override
    public int getId() {
        return MessageConstants.PLAYER_LEFT_SESSION_MESSAGE_ID;
    }
    
}
