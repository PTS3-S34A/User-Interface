package nl.socnet.message;

import nl.soccar.library.enumeration.HandbrakeAction;
import nl.soccar.library.enumeration.SteerAction;
import nl.soccar.library.enumeration.ThrottleAction;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;
import nl.socnet.message.handler.PlayerMovedMessageHandler;

/**
 * Message for notifying a movement of a player.
 * 
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.PLAYER_MOVED_MESSAGE_ID, handler = PlayerMovedMessageHandler.class)
public final class PlayerMovedMessage extends Message {

    private final SteerAction steerAction;
    private final HandbrakeAction handbrakeAction;
    private final ThrottleAction throttleAction;
    
    /**
     * Initializes a PlayerMoved message.
     * 
     * @param steerAction the steerAction of the player movement, not null.
     * @param handbrakeAction the handbrakeAction of the player movement, not null.
     * @param throttleAction the throttleAction of the player movement, not null.
     */
    public PlayerMovedMessage(SteerAction steerAction, HandbrakeAction handbrakeAction, ThrottleAction throttleAction) {
        this.steerAction = steerAction;
        this.handbrakeAction = handbrakeAction;
        this.throttleAction = throttleAction;
    }
    
    /**
     * Gets the SteerAction of the player movement.
     * 
     * @return SteerAction the SteerAction of the player movement. 
     */
    public SteerAction getSteerAction() {
        return steerAction;
    }
    
    /**
     * Gets the HandbrakeAction of the player movement.
     * 
     * @return HandbrakeAction the HandbrakeAction of the player movement.
     */
    public HandbrakeAction getHandbrakAction() {
        return handbrakeAction;
    }
    
    /**
     * Gets the ThrottleAction of the player movement.
     * 
     * @return ThrottleAction the ThrottleAciton of the player movement.
     */
    public ThrottleAction getThrottleAction() {
        return throttleAction;
    } 
    
    @Override
    public int getId() {
        return MessageConstants.PLAYER_MOVED_MESSAGE_ID;
    }
    
}
