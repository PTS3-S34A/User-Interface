package nl.socnet.message;

import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;
import nl.socnet.message.handler.PlayerSyncMessageHandler;

/**
 * Message for synching a player.
 * 
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.SYNC_POSITION_PLAYER_MESSAGE_ID, handler = PlayerSyncMessageHandler.class)
public final class PlayerSyncMessage extends Message {

    private final int playerId;

    private final float x;
    private final float y;
    private final float angle;

    private final float linearVelocityX;
    private final float linearVelocityY;

    private final float angularVelocity;

    /**
     * Initializes a PlayerSync message.
     * 
     * @param playerId the PlayerId of the player that get synched, not null.
     * @param x The X-position of the player, not null.
     * @param y The Y-position of the player, not null.
     * @param angle The angle of the player, not null.
     * @param linearVelocityX the X-velocity of the player, not null.
     * @param linearVelocityY the Y-velocity of the player, not null.
     * @param angularVelocity the angle of the velocity, not null.
     */
    public PlayerSyncMessage(int playerId, float x, float y, float angle, float linearVelocityX, float linearVelocityY, float angularVelocity) {
        this.playerId = playerId;
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.linearVelocityX = linearVelocityX;
        this.linearVelocityY = linearVelocityY;
        this.angularVelocity = angularVelocity;
    }

    /**
     * Gets the playerID of the player that gets synched.
     * 
     * @return PlayerId Id of the player that gets synched. 
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Gets the X-position of the player.
     * 
     * @return float that X-position of the player. 
     */
    public float getX() {
        return x;
    }

    /**
     * Gets the Y-position of the player.
     * 
     * @return float the Y-position of the player.
     */
    public float getY() {
        return y;
    }

    /**
     * Gets the angle of the player.
     * 
     * @return float the Angle of the player.
     */
    public float getAngle() {
        return angle;
    }

    /**
     * Gets the linear x-velocity of the player.
     * 
     * @return float the x-velocity of the player. 
     */
    public float getLinearVelocityX() {
        return linearVelocityX;
    }

    /**
     * Gets the linear y-velocity of the player
     * 
     * @return float the y-velocity of the player. 
     */
    public float getLinearVelocityY() {
        return linearVelocityY;
    }

    /**
     * Gets the angle of the velocity.
     * 
     * @return float the angle of the velocity. 
     */
    public float getAngularVelocity() {
        return angularVelocity;
    }

    @Override
    public int getId() {
        return MessageConstants.SYNC_POSITION_PLAYER_MESSAGE_ID;
    }

}
