package nl.soccar.ui.fx.controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import nl.soccar.library.Statistics;

/**
 * A table-item represents one single row inside a (JavaFX) TableView.
 *
 * @author PTS34A
 */
public class StatisticsTableItem {

    private final SimpleStringProperty username;
    private final SimpleIntegerProperty goals;
    private final SimpleIntegerProperty assists;
    private final SimpleStringProperty ratio;
    private final SimpleIntegerProperty gamesWon;
    private final SimpleIntegerProperty gamesEven;
    private final SimpleIntegerProperty gamesLost;

    /**
     * Initiates a new StatisticsTableItem using the given session.
     *
     * @param statistics The statistics of which values will be retreived from.
     */
    public StatisticsTableItem(Statistics statistics) {
        username = new SimpleStringProperty(statistics.getUsername());
        goals = new SimpleIntegerProperty(statistics.getGoals());
        assists = new SimpleIntegerProperty(statistics.getAssists());
        ratio = new SimpleStringProperty(String.format("%.1f%%", statistics.getGamesRatio()));
        gamesWon = new SimpleIntegerProperty(statistics.getGamesWon());
        gamesEven = new SimpleIntegerProperty(statistics.getGamesPlayed() - statistics.getGamesWon() - statistics.getGamesLost());
        gamesLost = new SimpleIntegerProperty(statistics.getGamesLost());
    }

    /**
     * Gets the username that belongs to the statistics.
     *
     * @return String, username.
     */
    public String getUsername() {
        return username.get();
    }

    /**
     * Gets the total of goals from a player.
     *
     * @return int, the total of goals.
     */
    public Integer getGoals() {
        return goals.get();
    }

    /**
     * Gets the total of assists from a player.
     *
     * @return int, the total of assists.
     */
    public Integer getAssists() {
        return assists.get();
    }

    /**
     * Gets the ratio games won/lost/played.
     *
     * @return int, the total of games won/lost/played.
     */
    public String getRatio() {
        return ratio.get();
    }

    /**
     * Gets the total of games won.
     *
     * @return int, the total of goals won.
     */
    public Integer getGamesWon() {
        return gamesWon.get();
    }

    /**
     * Gets the total of games even.
     *
     * @return int, the total of goals even.
     */
    public Integer getGamesEven() {
        return gamesEven.get();
    }

    /**
     * Gets the total of games lost.
     *
     * @return int, the total of goals scored.
     */
    public Integer getGamesLost() {
        return gamesLost.get();
    }

}
