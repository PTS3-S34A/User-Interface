package nl.socnet.message;

import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;
import nl.socnet.message.handler.PlayerLeftSessionMessageHandler;

/**
 * Message for notifying that a player left.
 * 
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.PLAYER_LEFT_SESSION_MESSAGE_ID, handler = PlayerLeftSessionMessageHandler.class)
public final class PlayerLeftSessionMessage extends Message {

    private final int playerId;

    /**
     * Initializes the PlayerLeftSession message.
     * 
     * @param playerId the playerId of the player that left the session, not null.
     */
    public PlayerLeftSessionMessage(int playerId) {
        this.playerId = playerId;
    }

    /**
     * Gets the playerID of the player that left the session.
     * 
     * @return PlayerId that playerID of the player that left the session.
     */
    public int getPlayerId() {
        return playerId;
    }

    @Override
    public int getId() {
        return MessageConstants.PLAYER_LEFT_SESSION_MESSAGE_ID;
    }
    
}
