package nl.socnet.message;

import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;
import nl.socnet.message.handler.SpawnCarMessageHandler;

/**
 * Message for spawing a car on the map.
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.SPAWN_CAR_MESSAGE_ID, handler = SpawnCarMessageHandler.class)
public final class SpawnCarMessage extends Message {

    private final int playerId;
    private final float x;
    private final float y;
    private final float angle;

    /**
     * Initializes a SpawnCar message.
     * 
     * @param playerId the playerId of the player that the car belongs to, not null.
     * @param x the x-position that the car gets spawned, not null.
     * @param y the y-position that the car gets spawned, not null.
     * @param angle the angle that the car gets spawned in, not null.
     */
    public SpawnCarMessage(int playerId, float x, float y, float angle) {
        this.playerId = playerId;
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    /**
     * Gets the PlayerId the car belongs to.
     *
     * @return int, the playerId.
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Gets the X-position of the car.
     *
     * @return float, the x-position.
     */
    public float getX() {
        return x;
    }

    /**
     * Gets the Y--position of the car.
     *
     * @return float, the y-position.
     */
    public float getY() {
        return y;
    }

    /**
     * Gets the angle of the car.
     *
     * @return float, the angle.
     */
    public float getAngle() {
        return angle;
    }

    @Override
    public int getId() {
        return MessageConstants.SPAWN_CAR_MESSAGE_ID;
    }

}
