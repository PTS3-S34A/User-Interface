package nl.socnet.message;

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

    private final int playerId;

    public PlayerLeftSessionMessage(int playerId) {
        this.playerId = playerId;
    }

    public int getPlayerId() {
        return playerId;
    }

    @Override
    public int getId() {
        return MessageConstants.PLAYER_LEFT_SESSION_MESSAGE_ID;
    }
    
}
