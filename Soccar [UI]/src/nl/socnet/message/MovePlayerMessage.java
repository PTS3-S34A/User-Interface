package nl.socnet.message;

import nl.soccar.library.enumeration.HandbrakeAction;
import nl.soccar.library.enumeration.SteerAction;
import nl.soccar.library.enumeration.ThrottleAction;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;
import nl.socnet.message.handler.MovePlayerMessageHandler;

/**
 * Message for a player movement.
 * 
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.MOVE_PLAYER_MESSAGE_ID, handler = MovePlayerMessageHandler.class)
public final class MovePlayerMessage extends Message {

    private final int playerId;
    private final SteerAction steerAction;
    private final HandbrakeAction handbrakeAction;
    private final ThrottleAction throttleAction;

    /**
     * Initializes the player movement message.
     * 
     * @param playerId The Id of the moving Player, not null.
     * @param steerAction The steerAction of the movement, not null.
     * @param handbrakeAction The handbrakeAction of the movement, not null.
     * @param throttleAction The throttleAction of the movement, not null.
     */
    public MovePlayerMessage(int playerId, SteerAction steerAction, HandbrakeAction handbrakeAction, ThrottleAction throttleAction) {
        this.playerId = playerId;
        this.steerAction = steerAction;
        this.handbrakeAction = handbrakeAction;
        this.throttleAction = throttleAction;
    }

    /**
     * Gets the playerId of the moving player.
     * 
     * @return int The playerID of the moving player.
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Gets the steerAction of the movement.
     * 
     * @return SteerAction The steerAction of the movement.
     */
    public SteerAction getSteerAction() {
        return steerAction;
    }

    /**
     * Gets the handbrakeAction of the movement.
     * 
     * @return HandbrakeAction The handbrakeAction of the movement. 
     */
    public HandbrakeAction getHandbrakAction() {
        return handbrakeAction;
    }

    /**
     * Gets the throttleAction of the movement.
     * 
     * @return ThrottleAction the throttleAction of the movement.
     */
    public ThrottleAction getThrottleAction() {
        return throttleAction;
    }

    @Override
    public int getId() {
        return MessageConstants.MOVE_PLAYER_MESSAGE_ID;
    }

}
