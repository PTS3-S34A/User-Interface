package nl.socnet.message;

import nl.soccar.library.enumeration.ObstacleType;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;
import nl.socnet.message.handler.SpawnObstacleMessageHandler;

/**
 * Message for Spawning obstacles on the map.
 *
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.SPAWN_OBSTACLE_MESSAGE_ID, handler = SpawnObstacleMessageHandler.class)
public final class SpawnObstacleMessage extends Message {

    private final float x;
    private final float y;
    private final float angle;

    private final float width;
    private final float height;
    private final ObstacleType type;

    /**
     * Initializes a SpawnObstacle message.
     *
     * @param x the x-position of the obstacle, not null.
     * @param y the y-position of the obstacle, not null.
     * @param angle the angle of the obstacle, not null.
     * @param width the width of the obstacle, not null.
     * @param height the height of the obstacle, not null.
     * @param type the ObstacleType of the obstacle, not null.
     */
    public SpawnObstacleMessage(float x, float y, float angle, float width, float height, ObstacleType type) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.width = width;
        this.height = height;
        this.type = type;
    }

    /**
     * Gets the X-position of the obstacle.
     *
     * @return float, the x-position.
     */
    public float getX() {
        return x;
    }

    /**
     * Gets the X-position of the obstacle.
     *
     * @return float, the y-position.
     */
    public float getY() {
        return y;
    }

    /**
     * Gets the angle of the obstacle.
     *
     * @return float, the angle.
     */
    public float getAngle() {
        return angle;
    }

    /**
     * Gets the width of the obstacle.
     *
     * @return float, the width.
     */
    public float getWidth() {
        return width;
    }

    /**
     * Gets the height of the obstacle.
     *
     * @return float, the height.
     */
    public float getHeight() {
        return height;
    }

    /**
     * Gets the obstacleType of the obstacle.
     *
     * @return float, the obstacleType.
     */
    public ObstacleType getObstacleType() {
        return type;
    }

    @Override
    public int getId() {
        return MessageConstants.SPAWN_OBSTACLE_MESSAGE_ID;
    }

}
