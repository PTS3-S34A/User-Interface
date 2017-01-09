package nl.socnet.message;

import nl.soccar.library.enumeration.BallType;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;
import nl.socnet.message.handler.SpawnBallMessageHandler;

/**
 * Message for spawing a ball on the map.
 *
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.SPAWN_BALL_MESSAGE_ID, handler = SpawnBallMessageHandler.class)
public final class SpawnBallMessage extends Message {

    private final float x;
    private final float y;
    private final float angle;

    private final BallType type;

    /**
     * Initializes a SpawnBall message.
     *
     * @param x The x-position of the ball, not null.
     * @param y The y-position of the ball, not null.
     * @param angle the angle of the ball, not null.
     * @param type the type of the ball, not null.
     */
    public SpawnBallMessage(float x, float y, float angle, BallType type) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.type = type;
    }

    /**
     * Gets the X-position of the ball.
     *
     * @return float, the x-position.
     */
    public float getX() {
        return x;
    }

    /**
     * Gets the Y-position of the ball.
     *
     * @return float, the y-position.
     */
    public float getY() {
        return y;
    }

    /**
     * Gets the angle the ball is in.
     *
     * @return float, the angle the ball is in.
     */
    public float getAngle() {
        return angle;
    }

    /**
     * Gets the BallType of the ball.
     *
     * @return float, the BallType.
     */
    public BallType getBallType() {
        return type;
    }

    @Override
    public int getId() {
        return MessageConstants.SPAWN_BALL_MESSAGE_ID;
    }

}
