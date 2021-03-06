package nl.soccar.ui.fx.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import nl.soccar.library.*;
import nl.soccar.library.enumeration.EventType;
import nl.soccar.ui.Main;
import nl.soccar.ui.fx.FXMLConstants;
import nl.soccar.ui.rmi.ClientController;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author PTS34A
 */
public class GameResultsFXMLController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(GameResultsFXMLController.class.getSimpleName());
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
    private Session currentSession;
    
    private Player currentPlayer;
    
    private Room room;
    private Game game;
    
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
            LOGGER.log(Level.WARNING, "An exception occurred while getting the SessionData from the Game Server");
            return;
        }
    }

    /**
     * Method that displays a summary of the game on the GameResults scene.
     */
    public void setGameInfo() {
        room = currentSession.getRoom();
        game = currentSession.getGame();
        
        for (Player p : room.getTeamBlue().getPlayers()) {       
            ResultTableItem r = new ResultTableItem(p.getUsername(), countGoalsUser(EventType.GOAL_BLUE, p));
            tblBlueGoalsList.getItems().add(r);
        }
        
        for (Player p : room.getTeamRed().getPlayers()) {
            ResultTableItem r = new ResultTableItem(p.getUsername(), countGoalsUser(EventType.GOAL_RED, p));
            tblRedGoalsList.getItems().add(r);
        }
        
        StringBuilder scoreString = new StringBuilder();
        scoreString.append("RED ");
        scoreString.append(game.getEventCountByType(EventType.GOAL_RED));
        scoreString.append(" - ");
        scoreString.append(game.getEventCountByType(EventType.GOAL_BLUE));
        scoreString.append(" BLUE");
        
        lblScoreEnd.setText(scoreString.toString());
        lblRoomName.setText(room.getName());
    }
    
    /**
     * Method that counts goals for a user.
     * 
     * @param type Goaltype (team) that sould be checked, not null.
     * @param p Player that gets counted, not null.
     * @return int, Number of goals.
     */
    private int countGoalsUser(EventType type, Player p) {
        int goals = 0;
        for (Event e : game.getEvents()) {
            if (e.getType() == type && e.getPlayer().equals(p)) {
                goals++;
            }
        }
        return goals;
    }

    /**
     * Method that navigates to back the SessionView screen (Room information).
     */
    public void closeResults() {
        Main main = Main.getInstance();
        main.setScene(FXMLConstants.LOCATION_SESSION_VIEW);
        ((SessionViewFXMLController) main.getController()).setRoomInfo();
    }
    
}
