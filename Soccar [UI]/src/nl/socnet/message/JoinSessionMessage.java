package nl.socnet.message;

import nl.soccar.library.GameSettings;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;
import nl.socnet.message.handler.JoinSessionMessageHandler;

/**
 * Message for a session joinment.
 * 
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.JOIN_SESSION_MESSAGE_ID, handler = JoinSessionMessageHandler.class)
public final class JoinSessionMessage extends Message {

    /**
     * Enum for the status of the joinment.
     */
    public enum Status {
        SUCCESS,
        SESSION_NON_EXISTENT,
        INVALID_PASSWORD,
        CAPACITY_OVERFLOW,
        USERNAME_EXISTS
    }

    private final String roomName;
    private final String password;

    private final Status status;
    private final int capacity;
    private final GameSettings settings;

    /**
     * Initializes the JoinSession message.
     * 
     * @param status The status of the joinment, not null.
     */
    public JoinSessionMessage(Status status) {
        this.status = status;
        roomName = null;
        password = null;
        capacity = -1;
        settings = null;
    }

    /**
     * Initializes the JoinSession message.
     * 
     * @param roomName The roomName that the player wants to join, not null.
     * @param password The password that gets given, not null.
     */
    public JoinSessionMessage(String roomName, String password) {
        this.roomName = roomName;
        this.password = password;

        status = null;
        capacity = -1;
        settings = null;
    }

    /**
     * Initializes the JoinSession message.
     * 
     * @param status The status of the joinment.
     * @param roomName The RoomName of the room that gets joined, not null.
     * @param capacity The capacity of the room that gets joined, not null.
     * @param settings The settings of the room that gets joined, not null.
     */
    public JoinSessionMessage(Status status, String roomName, int capacity, GameSettings settings) {
        this.status = status;
        this.roomName = roomName;
        password = "";
        this.capacity = capacity;
        this.settings = settings;
    }

    /**
     * Gets the status of the game joinment.
     * 
     * @return Status The status of the joinment. 
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Gets the roomName of the room to join.
     * 
     * @return String The room name.
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     * Gets the roomPassword of the joinMessage.
     * 
     * @return String the Password given by the player. 
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the capacity of the room.
     * 
     * @return int The capacity of the room. 
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Gets the GameSetting of the game in the room.
     * @return GameSettings the settings of the joinable game.
     */
    public GameSettings getGameSettings() {
        return settings;
    }

    @Override
    public int getId() {
        return MessageConstants.JOIN_SESSION_MESSAGE_ID;
    }

}
