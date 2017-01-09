package nl.socnet.message;

import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;
import nl.socnet.message.handler.PlayerChangedGameStatusMessageHandler;

/**
 * Message for a GameStatus change by a player.
 * 
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.PLAYER_CHANGED_GAME_STATUS_MESSAGE_ID, handler = PlayerChangedGameStatusMessageHandler.class)
public final class PlayerChangedGameStatusMessage extends Message {

    private final Status status;

    /**
     * Initializes the PlayerChangedGameStatus Message.
     * 
     * @param status the new Status of a game, not null.
     */
    public PlayerChangedGameStatusMessage(Status status) {
        this.status = status;
    }

    /**
     * Gets the status of the message.
     * 
     * @return Status The new gameStatus in the message.
     */
    public Status getStatus() {
        return status;
    }

    @Override
    public int getId() {
        return MessageConstants.PLAYER_CHANGED_GAME_STATUS_MESSAGE_ID;
    }

    /**
     * Enum for the gameStatus.
     */
    public enum Status {
        GAME_PAUSED,
        GAME_RUNNING
    }

}
