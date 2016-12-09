package nl.socnet.message;

import nl.soccar.library.enumeration.HandbrakeAction;
import nl.soccar.library.enumeration.SteerAction;
import nl.soccar.library.enumeration.ThrottleAction;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;
import nl.socnet.message.handler.PlayerMovedMessageHandler;

/**
 *
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.PLAYER_MOVED_MESSAGE_ID, handler = PlayerMovedMessageHandler.class)
public final class PlayerMovedMessage extends Message {

    private final SteerAction steerAction;
    private final HandbrakeAction handbrakeAction;
    private final ThrottleAction throttleAction;
    
    public PlayerMovedMessage(SteerAction steerAction, HandbrakeAction handbrakeAction, ThrottleAction throttleAction) {
        this.steerAction = steerAction;
        this.handbrakeAction = handbrakeAction;
        this.throttleAction = throttleAction;
    }
    
    public SteerAction getSteerAction() {
        return steerAction;
    }
    
    public HandbrakeAction getHandbrakAction() {
        return handbrakeAction;
    }
    
    public ThrottleAction getThrottleAction() {
        return throttleAction;
    } 
    
    @Override
    public int getId() {
        return MessageConstants.PLAYER_MOVED_MESSAGE_ID;
    }
    
}
