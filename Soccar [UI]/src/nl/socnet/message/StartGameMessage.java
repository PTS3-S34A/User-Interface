package nl.socnet.message;

import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;
import nl.socnet.message.handler.StartGameMessageHandler;

/**
 *
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.START_GAME_MESSAGE_ID, handler = StartGameMessageHandler.class)
public final class StartGameMessage extends Message {

    @Override
    public int getId() {
        return MessageConstants.START_GAME_MESSAGE_ID;
    }
    
}
