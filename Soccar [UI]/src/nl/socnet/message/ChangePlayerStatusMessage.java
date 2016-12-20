package nl.socnet.message;

import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;
import nl.socnet.message.handler.ChangePlayerStatusMessageHandler;

/**
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.CHANGE_PLAYER_STATUS_MESSAGE_ID, handler = ChangePlayerStatusMessageHandler.class)
public final class ChangePlayerStatusMessage extends Message {

    private final Status status;

    public ChangePlayerStatusMessage(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public int getId() {
        return MessageConstants.CHANGE_PLAYER_STATUS_MESSAGE_ID;
    }

    public enum Status {
        REQUEST_WORLD_OBJECTS,
        READY_TO_PLAY
    }

}
