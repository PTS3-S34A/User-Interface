package nl.soccar.ui.fx.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.Callback;
import nl.soccar.library.Player;
import nl.soccar.library.Room;
import nl.soccar.library.Session;
import nl.soccar.library.SessionData;
import nl.soccar.library.enumeration.Privilege;
import nl.soccar.library.enumeration.TeamColour;
import nl.soccar.socnet.Client;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.ui.Main;
import nl.soccar.ui.fx.FXMLConstants;
import nl.soccar.ui.rmi.ClientController;
import nl.socnet.message.ChangeGameStatusMessage;
import nl.socnet.message.ChatMessage;
import nl.socnet.message.LeaveSessionMessage;
import nl.socnet.message.SwitchTeamMessage;

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
public class SessionViewFXMLController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(SessionViewFXMLController.class.getSimpleName());
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
    private ListView lvChat;
    @FXML
    private TextField txtChat;
    @FXML
    private Button btnChat;
    @FXML
    private Button btnSwitchToBlue;
    @FXML
    private Button btnSwitchToRed;
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

        btnSwitchToBlue.setOnAction(e -> switchTeam());
        btnSwitchToRed.setOnAction(e -> switchTeam());

        btnChat.disableProperty().bind(txtChat.textProperty().isEmpty().or(txtChat.textProperty().length().greaterThanOrEqualTo(75)));
        btnChat.setOnAction(e -> sendChatMessage());
        txtChat.setOnAction(e -> sendChatMessage());

        lvChat.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> list) {
                ListCell cell = new ListCell();

                Text text = new Text();
                text.wrappingWidthProperty().bind(lvChat.widthProperty().subtract(25));
                text.textProperty().bind(cell.itemProperty());

                cell.setGraphic(text);
                cell.setWrapText(true);
                return cell;
            }
        });

        setRoomInfo();
    }

    /**
     * Displays the current settings of the room on the session view.
     */
    public void setRoomInfo() {
        Room room = currentSession.getRoom();
        int occupancy = room.getOccupancy();
        int capacity = room.getCapacity();

        lblRoomName.setText(room.getName());
        lblOccupancy.setText(String.format("OCCUPATION: %d/%d", occupancy, capacity));

        lvPlayersBlue.setItems(FXCollections.observableArrayList(room.getTeamBlue().getPlayers()));
        lvPlayersRed.setItems(FXCollections.observableArrayList(room.getTeamRed().getPlayers()));

        Optional<SessionData> session = ClientController.getInstance().getAllSessions().stream().filter(s -> s.getRoomName().equals(lblRoomName.getText())).findFirst();
        if (!session.isPresent()) {
            LOGGER.log(Level.WARNING, "An exception occured while getting the SessionData from the Game Server");
            return;
        }

        TeamColour kut = room.getTeamRed().getPlayers().contains(currentPlayer) ? TeamColour.RED : TeamColour.BLUE;
        btnSwitchToRed.setDisable(kut == TeamColour.RED);
        btnSwitchToBlue.setDisable(kut == TeamColour.BLUE);

        btnStartGame.setVisible(session.get().getHostName().equals(currentPlayer.getUsername()));
        // TODO: btnStartGame.setDisable(occupancy != capacity);
    }

    /**
     * Removes the player from the current session and navigates to the main
     * menu view.
     */
    private void leaveRoom() {
        ClientController controller = ClientController.getInstance();

        Connection connection = controller.getCurrentConnection();
        connection.send(new LeaveSessionMessage());

        Main main = Main.getInstance();
        main.setScene(FXMLConstants.LOCATION_MAIN_MENU);

        Client client = controller.getClient();
        client.disconnect();

        controller.setCurrentConnection(null);
        currentPlayer.setCurrentSession(null);
    }

    /**
     * Navigates to the game view and set the application window to full screen
     * mode.
     */
    private void startGame() {
        Connection connection = ClientController.getInstance().getCurrentConnection();

        connection.send(new ChangeGameStatusMessage());
    }

    /**
     * Sends the chatmessage to all players in this session.
     */
    private void sendChatMessage() {
        String message = txtChat.getText();
        if (message.trim().isEmpty() || message.length() >= 75) {
            return;
        }

        ClientController.getInstance().getCurrentConnection().send(new ChatMessage(message));

        txtChat.clear();
    }

    /**
     * Adds a new chat message to the list view that is used to display all chat
     * messages.
     *
     * @param username The username of the player that sent the chatmessage.
     * @param privilege The privilege of the player that sent the chatmessage.
     * @param message The actual content of the message.
     */
    public void addChatMessage(String username, Privilege privilege, String message) {
        lvChat.getItems().add(String.format("%s%s: %s", getPrivilegePrefix(privilege), username, message));
        lvChat.scrollTo(lvChat.getItems().size() - 1);
    }

    /**
     * Gets the privilege prefix that is shown in front of a username is a chat
     * message.
     *
     * @param privilege The privilege that needs to be shortened.
     * @return The shortened privilege.
     */
    private String getPrivilegePrefix(Privilege privilege) {
        if (privilege == null) {
            return "";
        }

        switch (privilege) {
            case ADMINISTRATOR:
                return "[ADMIN] ";
            case NORMAL:
                return "[R] ";
            case GUEST:
                return "";
            default:
                throw new UnsupportedOperationException("Unknown user privilege");
        }
    }

    private void switchTeam() {
        ClientController.getInstance().getCurrentConnection().send(new SwitchTeamMessage());
    }

}
