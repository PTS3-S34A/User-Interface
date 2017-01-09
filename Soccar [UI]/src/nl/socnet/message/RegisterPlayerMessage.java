package nl.socnet.message;

import nl.socnet.message.handler.RegisterPlayerMessageHandler;
import nl.soccar.library.enumeration.CarType;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;

/**
 * Message for registering a player on the gameserver.
 * 
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.REGISTER_PLAYER_MESSAGE_ID, handler = RegisterPlayerMessageHandler.class)
public final class RegisterPlayerMessage extends Message{
        
    private final String username;
    private final CarType type;

    /**
     * Initializes a RegisterPlayer message.
     * 
     * @param username Username of the player that gets registered to the gameserver, not null.
     * @param type CarType of the player, not null.
     */
    public RegisterPlayerMessage(String username, CarType type) {
        this.username = username;
        this.type = type;
    }
    
    /** 
     * Gets the username of the registering player.
     * 
     * @return String the username of the player.
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * Gets the CarType of the registering player.
     * 
     * @return CarType the carType of the player.
     */
    public CarType getCarType() {
        return type;
    }
    
    @Override
    public int getId() {
        return MessageConstants.REGISTER_PLAYER_MESSAGE_ID;
    }
    
}
