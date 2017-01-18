package nl.socnet.message;

import nl.soccar.library.enumeration.EventType;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;
import nl.socnet.message.handler.EventMessageHandler;

/**
 * Message for occurring events.
 *
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.EVENT_MESSAGE_ID, handler = EventMessageHandler.class)
public final class EventMessage extends Message {

    private final EventType type;
    private final int playerId;

    /**
     * Initializes this message.
     *
     * @param type  The EventType of this message.
     * @param playerId The id of the new host.
     */
    public EventMessage(EventType type, int playerId) {
        this.type = type;
        this.playerId = playerId;
    }

    /**
     * Gets the EventType of this Message.
     *
     * @return The EventType of this Message.
     */
    public EventType getEventType() {
        return type;
    }

    /**
     * The username of the new host.
     *
     * @return The id of the new host.
     */
    public int getPlayerId() {
        return playerId;
    }

    @Override
    public int getId() {
        return MessageConstants.EVENT_MESSAGE_ID;
    }

}
