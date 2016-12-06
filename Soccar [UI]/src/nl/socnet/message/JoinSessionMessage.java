package nl.socnet.message;

import nl.soccar.library.GameSettings;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageEvent;
import nl.socnet.message.handler.JoinSessionMessageHandler;

/**
 *
 * @author PTS34A
 */
@MessageEvent(id = 2, handler = JoinSessionMessageHandler.class)
public final class JoinSessionMessage extends Message {

    public enum Status {
        SUCCESS
    }
    
    private static final int JOIN_SESSION_MESSAGE_ID = 2;
    
    private final String roomName;
    private final String password;
    
    private final Status status;
    private final int capacity;
    private final GameSettings settings;

    public JoinSessionMessage(String roomName, String password) {
        status = null;
        this.roomName = roomName;
        this.password = password;
        capacity = -1;
        settings = null;
    }
    
    public JoinSessionMessage(Status status, String roomName, int capacity, GameSettings settings) {
        this.status = status;
        this.roomName = roomName;
        password = "";
        this.capacity = capacity;
        this.settings = settings;
    }
    
    public Status getStatus() {
        return status;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getPassword() {
        return password;
    }
    
    public int getCapacity() {
        return capacity;
    }
    
    public GameSettings getGameSettings() {
        return settings;
    }

    @Override
    public int getId() {
        return JOIN_SESSION_MESSAGE_ID;
    }

}
