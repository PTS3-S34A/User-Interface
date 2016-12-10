package nl.soccar.ui.fx.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import nl.soccar.library.Event;
import nl.soccar.library.Game;
import nl.soccar.library.Player;
import nl.soccar.library.Room;
import nl.soccar.library.Session;
import nl.soccar.library.SessionData;
import nl.soccar.library.enumeration.EventType;
import nl.soccar.ui.rmi.ClientController;
import nl.soccar.ui.Main;
import nl.soccar.ui.fx.FXMLConstants;

/**
 * FXML Controller class
 *
 * @author PTS34A
 */
public class GameResultsFXMLController implements Initializable {

    @FXML
    private Label lblRoomName;
    @FXML
    private Label lblScoreEnd;
    @FXML
    private TableColumn tbclBlueGoalsScored;
    @FXML
    private TableColumn tbclBlueUsername;
    @FXML
    private TableColumn tbclRedGoalsScored;
    @FXML
    private TableColumn tbclRedUsername;
    @FXML
    private Button btnExitResults;
    @FXML
    private Button btnLogOut;
    @FXML
    private Label lblUsername;
    @FXML
    private Label lblCar;
    @FXML
    private TableView<ResultTableItem> tblBlueGoalsList;
    @FXML
    private TableView<ResultTableItem> tblRedGoalsList;

    private static final Logger LOGGER = Logger.getLogger(GameResultsFXMLController.class.getSimpleName());

    private Session currentSession;

    private Player currentPlayer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ClientController controller = ClientController.getInstance();
        currentPlayer = controller.getCurrentPlayer();
        currentSession = currentPlayer.getCurrentSession(); // Will never be null.

        btnLogOut.setOnAction(e -> Main.getInstance().logOut());
        btnExitResults.setOnAction(e -> closeResults());

        tbclBlueUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        tbclRedUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        tbclBlueGoalsScored.setCellValueFactory(new PropertyValueFactory<>("goalsScored"));
        tbclRedGoalsScored.setCellValueFactory(new PropertyValueFactory<>("goalsScored"));

        lblUsername.setText(currentPlayer.getUsername());
        lblCar.setText(currentPlayer.getCarType().toString());

        setGameInfo();

        Optional<SessionData> session = controller.getAllSessions().stream().filter(s -> s.getRoomName().equals(lblRoomName.getText())).findFirst();
        if (!session.isPresent()) {
            LOGGER.log(Level.WARNING, "An exception occured while getting the SessionData from the Game Server");
            return;
        }
    }

    /**
     * Method that displays a summary of the game on the GameResults scene.
     */
    public void setGameInfo() {
        Room room = currentSession.getRoom();
        Game game = currentSession.getGame();

        for (Player p : room.getTeamBlue().getPlayers()) {
            int goals = 0;
            for (Event e : game.getEvents()) {
                if (e.getType() == EventType.GOAL_BLUE && e.getPlayer().equals(p)) {
                    goals++;
                }
            }

            ResultTableItem r = new ResultTableItem(p.getUsername(), goals);
            tblBlueGoalsList.getItems().add(r);
        }

        for (Player p : room.getTeamRed().getPlayers()) {
            int goals = 0;
            for (Event e : game.getEvents()) {
                if (e.getType() == EventType.GOAL_BLUE && e.getPlayer().equals(p)) {
                    goals++;
                }
            }

            ResultTableItem r = new ResultTableItem(p.getUsername(), goals);
            tblRedGoalsList.getItems().add(r);
        }

        StringBuilder scoreString = new StringBuilder();
        scoreString.append("RED ");
        scoreString.append(game.getEventCountByType(EventType.GOAL_RED));
        scoreString.append(" - ");
        scoreString.append(game.getEventCountByType(EventType.GOAL_BLUE));
        scoreString.append(" BLUE");

        lblScoreEnd.setText(scoreString.toString());
        lblRoomName.setText("Game Results - " + room.getName());
    }

    /**
     * Method that navigates to back the SessionView screen (Room information).
     */
    public void closeResults() {
        Main.getInstance().setScene(FXMLConstants.LOCATION_SESSION_VIEW);
    }

}
