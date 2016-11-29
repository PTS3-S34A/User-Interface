package nl.soccar.ui.fx.controller;

import javafx.beans.property.SimpleStringProperty;
import nl.soccar.library.Player;
import nl.soccar.library.Room;
import nl.soccar.library.Session;
import nl.soccar.library.SessionData;

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

    /**
     * Initiates a new SesionTableItem using the given session.
     * 
     * @param session The session of which values will be retreived from.
     */
    public SessionTableItem(SessionData session) {
        roomName = new SimpleStringProperty(session.getRoomName());
        occupancy = new SimpleStringProperty(String.format("%d/%d", session.getOccupation(), session.getCapacity()));
        hostName = new SimpleStringProperty(session.getHostName());
        passwordAvailable = new SimpleStringProperty(session.hasPassword() ? "Yes" : "No");
    }

    public String getRoomName() {
        return roomName.get();
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
