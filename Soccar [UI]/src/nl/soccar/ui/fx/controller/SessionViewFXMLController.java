package nl.soccar.ui.fx.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import nl.soccar.library.Game;
import nl.soccar.library.Player;
import nl.soccar.library.Room;
import nl.soccar.library.Session;
import nl.soccar.library.enumeration.TeamColour;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.library.SessionData;
import nl.soccar.socnet.Client;
import nl.soccar.ui.rmi.ClientController;
import nl.soccar.ui.Main;
import nl.soccar.ui.fx.FXMLConstants;
import nl.socnet.message.PlayerLeaveSessionMessage;
import nl.socnet.message.StartGameMessage;

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

    private static final Logger LOGGER = Logger.getLogger(SessionViewFXMLController.class.getSimpleName());

    private Session currentSession;

    private Player currentPlayer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ClientController controller = ClientController.getInstance();
        currentPlayer = controller.getCurrentPlayer();
        currentSession = currentPlayer.getCurrentSession(); // Will never be null.

        btnLogOut.setOnAction(e -> Main.getInstance().logOut());
        btnLeaveRoom.setOnAction(e -> leaveRoom());
        btnStartGame.setOnAction(e -> startGame());

        lblUsername.setText(currentPlayer.getUsername());
        lblCar.setText(currentPlayer.getCarType().toString());

        setRoomInfo();

        Optional<SessionData> session = controller.getAllSessions().stream().filter(s -> s.getRoomName().equals(lblRoomName.getText())).findFirst();
        if (!session.isPresent()) {
            LOGGER.log(Level.WARNING, "An exception occured while getting the SessionData from the Game Server");
            return;
        }

        btnStartGame.setVisible(session.get().getHostName().equals(currentPlayer.getUsername()));
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
        
        Client client = controller.getClient();
        client.disconnect();
        
        controller.setCurrentConnection(null);
        currentPlayer.setCurrentSession(null);
        
    }

    /**
     * Method that navigates to the game view and set the application window to
     * full screen mode.
     */
    public void startGame() {
        Connection connection = ClientController.getInstance().getCurrentConnection();
        
        connection.send(new StartGameMessage());
    }
}
