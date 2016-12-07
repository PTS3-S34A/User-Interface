package nl.socnet.message;

import nl.socnet.message.handler.RegisterPlayerMessageHandler;
import nl.soccar.library.enumeration.CarType;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;

/**
 *
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.REGISTER_PLAYER_MESSAGE_ID, handler = RegisterPlayerMessageHandler.class)
public final class RegisterPlayerMessage extends Message{
        
    private final String username;
    private final CarType type;

    public RegisterPlayerMessage(String username, CarType type) {
        this.username = username;
        this.type = type;
    }
    
    public String getUsername() {
        return username;
    }
    
    public CarType getCarType() {
        return type;
    }
    
    @Override
    public int getId() {
        return MessageConstants.REGISTER_PLAYER_MESSAGE_ID;
    }
    
}
