package nl.socnet.message;

import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;
import nl.socnet.message.handler.BallSyncMessageHandler;

/**
 * Message for the ball synchronization.
 * 
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.SYNC_POSITION_BALL_MESSAGE_ID, handler = BallSyncMessageHandler.class)
public final class BallSyncMessage extends Message {

    private final float x;
    private final float y;

    private final float linearVelocityX;
    private final float linearVelocityY;

    private final float angularVelocity;

    /**
     * Initializes the BallSyncMessage
     * 
     * @param x The current X-position, not null.
     * @param y The current Y-position, not null.
     * @param linearVelocityX The current linear x-velocity of the ball, not null.
     * @param linearVelocityY The current linear y-velocity of the ball, not null.
     * @param angularVelocity The current angle, not null.
     */
    public BallSyncMessage(float x, float y, float linearVelocityX, float linearVelocityY, float angularVelocity) {
        this.x = x;
        this.y = y;
        this.linearVelocityX = linearVelocityX;
        this.linearVelocityY = linearVelocityY;
        this.angularVelocity = angularVelocity;
    }

    /**
     * Gets the X-position of the ball.
     * 
     * @return float, the x-position of the ball.
     */
    public float getX() {
        return x;
    }

    /**
     * Gets the Y-position of the ball.
     * 
     * @return float, the y-position of the ball.
     */
    public float getY() {
        return y;
    }

    /**
     * Gets the X-velocity.
     * 
     * @return float, the x-velocity of the ball.
     */
    public float getLinearVelocityX() {
        return linearVelocityX;
    }

    /**
     * Gets the Y-velocity.
     * 
     * @return float, the y-velocity of the ball.
     */
    public float getLinearVelocityY() {
        return linearVelocityY;
    }

    /**
     * Gets the Angular velocity.
     * 
     * @return float, the angular velocity of the ball.
     */
    public float getAngularVelocity() {
        return angularVelocity;
    }

    @Override
    public int getId() {
        return MessageConstants.SYNC_POSITION_BALL_MESSAGE_ID;
    }

}
