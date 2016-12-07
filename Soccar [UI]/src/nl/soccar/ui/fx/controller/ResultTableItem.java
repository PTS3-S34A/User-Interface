package nl.soccar.ui.fx.controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * A table-item represents one single row inside a (JavaFX) TableView.
 * 
 * @author PTS34A
 */
public class ResultTableItem {

    private final SimpleStringProperty username;
    private final SimpleIntegerProperty goalsScored;

    /**
     * Initiates a new ResultTableItem using the given results.
     * 
     * @param username The username of the player the goals belong to.
     * @param goalsScored The amount of goals scored by the specific user.
     */
    public ResultTableItem(String username, Integer goalsScored) {
        this.username = new SimpleStringProperty(username);
        this.goalsScored = new SimpleIntegerProperty(goalsScored);
    }
    
    public String getUsername() {
        return username.get();
    }

    public Integer getGoalsScored() {
        return goalsScored.get();
    }
    
}
