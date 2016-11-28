package nl.soccar.ui.fx.controller;

import javafx.beans.property.SimpleStringProperty;
import nl.soccar.library.Player;
import nl.soccar.library.Room;
import nl.soccar.library.Session;

/**
 * A table-item represents one single row inside a (JavaFX) TableView.
 * 
 * @author PTS34A
 */
public class SessionTableItem {

    private final SimpleStringProperty roomName;
    private final SimpleStringProperty occupancy;
    private final SimpleStringProperty hostName;
    private final SimpleStringProperty passwordAvailable;
    
    private final Session session;

    /**
     * Initiates a new SesionTableItem using the given session.
     * 
     * @param s The session of which values will be retreived from.
     */
    public SessionTableItem(Session s) {
        this.session = s;
        
        Room room = s.getRoom();
        roomName = new SimpleStringProperty(room.getName());
        occupancy = new SimpleStringProperty(String.format("%d/%d", room.getOccupancy(), room.getCapacity()));
        
        Player host = room.getHost();
        hostName = new SimpleStringProperty(host != null ? host.getUsername() : "No host available");
        
        passwordAvailable = new SimpleStringProperty(room.passwordAvailable() ? "Yes" : "No");
    }

    public String getRoomName() {
        return roomName.get();
    }

    public Session getSession() {
        return session;
    }

    public String getOccupancy() {
        return occupancy.get();
    }

    public String getHostName() {
        return hostName.get();
    }

    public String isPasswordAvailable() {
        return passwordAvailable.get();
    }
}
