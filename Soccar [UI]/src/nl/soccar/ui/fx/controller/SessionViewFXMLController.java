package nl.soccar.ui.fx.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import nl.soccar.library.Player;
import nl.soccar.library.Room;
import nl.soccar.library.Session;
import nl.soccar.library.enumeration.TeamColour;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.ui.rmi.ClientController;
import nl.soccar.ui.Main;
import nl.soccar.ui.fx.FXMLConstants;
import nl.socnet.message.PlayerLeaveSessionMessage;

/**
 * FXML Controller class
 *
 * @author PTS34A
 */
public class SessionViewFXMLController implements Initializable {

    @FXML
    private Label lblRoomName;
    @FXML
    private ListView lvPlayersRed;
    @FXML
    private ListView lvPlayersBlue;
    @FXML
    private Button btnLeaveRoom;
    @FXML
    private Button btnLogOut;
    @FXML
    private Label lblUsername;
    @FXML
    private Label lblCar;
    @FXML
    private Label lblOccupancy;
    @FXML
    private Button btnStartGame;
    @FXML

    private Session currentSession;

    private Player currentPlayer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        currentPlayer = ClientController.getInstance().getCurrentPlayer();
        currentSession = currentPlayer.getCurrentSession(); // Will never be null.

        btnLogOut.setOnAction(e -> Main.getInstance().logOut());
        btnLeaveRoom.setOnAction(e -> leaveRoom());
        btnStartGame.setOnAction(e -> startGame());

        lblUsername.setText(currentPlayer.getUsername());
        lblCar.setText(currentPlayer.getCarType().toString());

        setRoomInfo();
        
        if (currentSession.getRoom().getHost().equals(currentPlayer)) {
            btnStartGame.setVisible(true);
        }
    }

    /**
     * Method that display the current settings of the room on the session view.
     */
    public void setRoomInfo() {
        Room room = currentSession.getRoom();
        int occupancy = room.getOccupancy();
        int capacity = room.getCapacity();

        lblRoomName.setText(room.getName());
        lblOccupancy.setText(String.format("OCCUPANCY: %d/%d", occupancy, capacity));

        lvPlayersBlue.setItems(FXCollections.observableArrayList(room.getTeamBlue().getPlayers()));
        lvPlayersRed.setItems(FXCollections.observableArrayList(room.getTeamRed().getPlayers()));

        // TODO: btnStartGame.setDisable(occupancy != capacity);
    }

    /**
     * Method that removes the player from the current session and navigates to
     * the main menu view.
     */
    private void leaveRoom() {
        ClientController controller = ClientController.getInstance();
        Connection connection = controller.getCurrentConnection();
        Room room = currentPlayer.getCurrentSession().getRoom();
        
        TeamColour colour = room.getTeamBlue().getPlayers().stream().filter(p -> p.getUsername().equals(currentPlayer.getUsername())).count() > 0 ? TeamColour.BLUE: TeamColour.RED;
        
        connection.send(new PlayerLeaveSessionMessage(currentPlayer.getUsername(), colour));
        
        Main main = Main.getInstance();
        main.setScene(FXMLConstants.LOCATION_MAIN_MENU);
        controller.setCurrentConnection(null);
        currentPlayer.setCurrentSession(null);
        
    }

    /**
     * Method that navigates to the game view and set the application window to
     * full screen mode.
     */
    public void startGame() {
        Main main = Main.getInstance();
        main.setScene(FXMLConstants.LOCATION_GAME_VIEW);
        main.setFullScreen(true);
    }
}
