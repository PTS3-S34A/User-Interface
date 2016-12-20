package nl.socnet.message;

import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;
import nl.socnet.message.handler.LeaveSessionMessageHandler;

/**
 *
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.PLAYER_LEAVE_SESSION_MESSAGE_ID, handler = LeaveSessionMessageHandler.class)
public class LeaveSessionMessage extends Message {
    
    @Override
    public int getId() {
        return MessageConstants.PLAYER_LEAVE_SESSION_MESSAGE_ID;
    }
    
}
