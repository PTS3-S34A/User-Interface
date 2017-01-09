package nl.socnet.message;

import nl.soccar.library.enumeration.Privilege;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;
import nl.socnet.message.handler.ChatMessageHandler;

/**
 * Message for a ChatMessage.
 * 
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.CHAT_MESSAGE_ID, handler = ChatMessageHandler.class)
public final class ChatMessage extends Message {

    private final int playerId;
    private final Privilege privilege;
    private final String message;

    /**
     * Initializes a ChatMessage Message.
     * 
     * @param playerId The ID of the player that sends the message, not null.
     * @param privilege The privilage of the sending player, not null.
     * @param message The message that gets send, not null.
     */
    public ChatMessage(int playerId, Privilege privilege, String message) {
        this.playerId = playerId;
        this.privilege = privilege;
        this.message = message;
    }

    /**
     * Initializes a ChatMessage Message
     * 
     * @param message The message that gets send, not null.
     */
    public ChatMessage(String message) {
        playerId = 0;
        privilege = null;
        this.message = message;
    }

    /**
     * Gets the PlayerID of the sending player.
     * 
     * @return int The sending PlayerId.
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Gets the Privilige of the sending player.
     * 
     * @return Privilege the Privilege of the sending player.
     */
    public Privilege getPrivilege() {
        return privilege;
    }

    /**
     * Gets the message content.
     * 
     * @return String The content of the message.
     */
    public String getMessage() {
        return message;
    }

    @Override
    public int getId() {
        return MessageConstants.CHAT_MESSAGE_ID;
    }

}
