package nl.socnet.message;

import nl.soccar.library.enumeration.Privilege;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;
import nl.socnet.message.handler.ChatMessageHandler;

/**
 *
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.CHAT_MESSAGE_ID, handler = ChatMessageHandler.class)
public final class ChatMessage extends Message {

    private final String username;
    private final Privilege privilege;
    private final String message;

    public ChatMessage(String username, Privilege privilege, String message) {
        this.username = username;
        this.privilege = privilege;
        this.message = message;
    }

    public ChatMessage(String message) {
        username = null;
        privilege = null;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public Privilege getPrivilege() {
        return privilege;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public int getId() {
        return MessageConstants.CHAT_MESSAGE_ID;
    }

}
