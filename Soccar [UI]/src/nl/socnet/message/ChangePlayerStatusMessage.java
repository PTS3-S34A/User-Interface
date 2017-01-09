package nl.socnet.message;

import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;
import nl.socnet.message.handler.ChangePlayerStatusMessageHandler;

/**
 * Message for changing the PlayerStatus. 
 * 
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.CHANGE_PLAYER_STATUS_MESSAGE_ID, handler = ChangePlayerStatusMessageHandler.class)
public final class ChangePlayerStatusMessage extends Message {

    private final Status status;

    /**
     * Initializes the ChangePlayerStatusMessage
     * 
     * @param status the current Status of a player, not null.
     */
    public ChangePlayerStatusMessage(Status status) {
        this.status = status;
    }

    /**
     * Gets the status of a player.
     * @return Status the status of the player (enum value).
     */
    public Status getStatus() {
        return status;
    }

    @Override
    public int getId() {
        return MessageConstants.CHANGE_PLAYER_STATUS_MESSAGE_ID;
    }

    /**
     * Enum for the status of the player.
     */
    public enum Status {
        REQUEST_WORLD_OBJECTS,
        READY_TO_PLAY
    }

}
