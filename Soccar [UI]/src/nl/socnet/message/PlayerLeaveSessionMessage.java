package nl.socnet.message;

import nl.soccar.library.enumeration.TeamColour;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageEvent;
import nl.socnet.message.handler.PlayerLeaveSessionMessageHandler;

/**
 *
 * @author PTS34A
 */
@MessageEvent(id = 5, handler = PlayerLeaveSessionMessageHandler.class)
public class PlayerLeaveSessionMessage extends Message {

    private static final int PLAYER_LEAVE_SESSION_MESSAGE_ID = 5;
    
    private final String username;
    private final TeamColour colour;
    
    public PlayerLeaveSessionMessage(String username, TeamColour colour) {
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
        return PLAYER_LEAVE_SESSION_MESSAGE_ID;
    }
    
}
