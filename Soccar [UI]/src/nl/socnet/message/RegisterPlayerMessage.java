package nl.socnet.message;

import nl.socnet.message.handler.RegisterPlayerMessageHandler;
import nl.soccar.library.enumeration.CarType;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageEvent;

/**
 *
 * @author PTS34A
 */
@MessageEvent(id = 1, handler = RegisterPlayerMessageHandler.class)
public final class RegisterPlayerMessage extends Message{
    
    private static final int REGISTER_PLAYER_MESSAGE_ID = 1;
    
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
        return REGISTER_PLAYER_MESSAGE_ID;
    }
    
}
