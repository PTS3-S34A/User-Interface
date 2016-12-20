package nl.socnet.message;

import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;
import nl.socnet.message.handler.PlayerChangedGameStatusMessageHandler;

/**
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.PLAYER_CHANGED_GAME_STATUS_MESSAGE_ID, handler = PlayerChangedGameStatusMessageHandler.class)
public final class PlayerChangedGameStatusMessage extends Message {

    private final Status status;

    public PlayerChangedGameStatusMessage(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public int getId() {
        return MessageConstants.PLAYER_CHANGED_GAME_STATUS_MESSAGE_ID;
    }

    public enum Status {
        GAME_PAUSED,
        GAME_RUNNING
    }

}
