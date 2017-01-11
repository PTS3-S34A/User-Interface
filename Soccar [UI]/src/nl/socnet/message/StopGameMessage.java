package nl.socnet.message;

import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;
import nl.socnet.message.handler.StopGameMessageHandler;

/**
 * Message for stopping the Game.
 * 
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.STOP_GAME_MESSAGE_ID, handler = StopGameMessageHandler.class)
public final class StopGameMessage extends Message {

    @Override
    public int getId() {
        return MessageConstants.STOP_GAME_MESSAGE_ID;
    }
    
}
