package nl.soccar.ui.fx.controller;

import javafx.beans.property.SimpleStringProperty;
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
    
    private final SessionData sessionData;

    /**
     * Initiates a new SesionTableItem using the given session.
     * 
     * @param session The session of which values will be retreived from.
     */
    public SessionTableItem(SessionData session) {
        this.sessionData = session;
        
        roomName = new SimpleStringProperty(session.getRoomName());
        occupancy = new SimpleStringProperty(String.format("%d/%d", session.getOccupation(), session.getCapacity()));
        hostName = new SimpleStringProperty(session.getHostName());
        passwordAvailable = new SimpleStringProperty(session.hasPassword() ? "Yes" : "No");
    }
    
    /**
     * Gets the sessionData of this session.
     * 
     * @return SessionData, of this session.
     */
    public SessionData getSessionData() {
        return sessionData;
    }

    /**
     * Gets the roomName.
     * @return String, the roomName
     */
    public String getRoomName() {
        return roomName.get();
    }

    /**
     * Gets the occupancy of the session.
     * 
     * @return String, the occupancy. 
     */
    public String getOccupancy() {
        return occupancy.get();
    }

    /**
     * Gets the hostName of the gameserver the game is hosted on.
     * @return String the hostName.
     */
    public String getHostName() {
        return hostName.get();
    }

    /**
     * Method that checks if there is a password available.
     * 
     * @return String password available.
     */
    public String isPasswordAvailable() {
        return passwordAvailable.get();
    }
    
}
