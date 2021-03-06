package nl.soccar.ui.fx.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import nl.soccar.exception.DuplicateValueException;
import nl.soccar.library.Player;
import nl.soccar.library.SessionData;
import nl.soccar.library.enumeration.BallType;
import nl.soccar.library.enumeration.Duration;
import nl.soccar.library.enumeration.MapType;
import nl.soccar.socnet.Client;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.ui.DisplayUtilities;
import nl.soccar.ui.Main;
import nl.soccar.ui.fx.FXMLConstants;
import nl.soccar.ui.rmi.ClientController;
import nl.soccar.ui.util.FxUtilities;
import nl.socnet.message.JoinSessionMessage;
import nl.socnet.message.RegisterPlayerMessage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * FXML Controller class
 *
 * @author PTS34A
 */
public class CreateRoomFXMLController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(CreateRoomFXMLController.class.getSimpleName());

    private static final String REGEX = "^[a-zA-Z0-9]{1,16}$";

    @FXML
    private Button btnLogOut;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnCreateRoom;
    @FXML
    private Label lblUsername;
    @FXML
    private Label lblCar;
    @FXML
    private TextField textFieldRoomName;
    @FXML
    private PasswordField textFieldPassword;
    @FXML
    private Slider sliderCapacity;
    @FXML
    private Slider sliderDuration;
    @FXML
    private ComboBox cbMap;
    @FXML
    private ComboBox cbBall;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnLogOut.setOnAction(e -> Main.getInstance().logOut());
        btnCancel.setOnAction(e -> Main.getInstance().setScene(FXMLConstants.LOCATION_MAIN_MENU));
        btnCreateRoom.setOnAction(e -> newRoomInformation());

        Player player = ClientController.getInstance().getCurrentPlayer();
        lblUsername.setText(player.getUsername());
        lblCar.setText(player.getCarType().toString());
        textFieldRoomName.setOnAction(e -> newRoomInformation());

        ObservableList<MapType> maps = cbMap.getItems();
        maps.addAll(MapType.values());
        cbMap.setValue(MapType.GRASSLAND);

        ObservableList<BallType> balls = cbBall.getItems();
        balls.addAll(BallType.values());
        cbBall.setValue(BallType.FOOTBALL);
    }

    /**
     * Checks the roominformatie-input before creating a room.
     */
    private void newRoomInformation() {
        String roomName = textFieldRoomName.getText();
        String input = textFieldPassword.getText();
        String password = !input.isEmpty() ? input : "";

        if (!checkInput(roomName)) {
            return;
        }

        int capacity = (int) sliderCapacity.getValue();
        Duration duration = Duration.values()[(int) sliderDuration.getValue() - 1];

        createRoom(roomName, password, capacity, duration);
    }

   /**
    * Creates a room (session) through the main-server.
    * 
    * @param roomName The roomname for this new room.
    * @param password The password for this new room.
    * @param capacity The capacity of the room.
    * @param duration The duration (length) for the game.
    */
    private void createRoom(String roomName, String password, int capacity, Duration duration) {

        ClientController controller = ClientController.getInstance();

        try {
            if (!controller.createSession(roomName, password, controller.getCurrentPlayer().getUsername(), capacity,
                    duration, (MapType) cbMap.getValue(), (BallType) cbBall.getValue())) {
                throw new DuplicateValueException("Error", "Roomname already exists.");
            }
        } catch (DuplicateValueException e) {
            LOGGER.log(Level.WARNING, "An error occurred while creating a room.", e);

            DisplayUtilities.showAlert(e.getTitle(), e.getMessage());

            return;
        }

        try {
            Client client = controller.getClient();
            Optional<SessionData> session = controller.getAllSessions().stream().filter(s -> s.getRoomName().equals(roomName)).findFirst();
            if (!session.isPresent()) {
                LOGGER.log(Level.WARNING, "An error occured while retrieving the session from the game server");
                return;
            }

            client.connect(session.get().getAddress(), 1046);

            Connection connection;
            while ((connection = controller.getCurrentConnection()) == null) {
                waitForConnection();
            }

            Player currentPlayer = controller.getCurrentPlayer();

            connection.send(new RegisterPlayerMessage(currentPlayer.getUsername(), currentPlayer.getCarType()));
            LOGGER.log(Level.INFO, "Registered Player to Game Server");

            connection.send(new JoinSessionMessage(roomName, password));
            LOGGER.log(Level.INFO, "Joined {0}", roomName);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "An exception occured while trying to connect to the Game Server", e);
        }
    }

    private void waitForConnection() {
        try {
            Thread.sleep(50L);
        } catch (InterruptedException e) {
            LOGGER.log(Level.WARNING, "An error while connecting to the game server.", e);

            DisplayUtilities.showAlert("Error", "An error while connecting to the game server.");

            return;
        }
    }

    /**
     * Checks the input before creating a room.
     *
     * @param roomName the input of the roomName field.
     * @param password the input of the password field.
     * @return boolean, input correct.
     */
    private boolean checkInput(String roomName) {
        textFieldRoomName.setStyle("-fx-text-box-border: white; -fx-focus-color: white;");

        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(roomName);

        boolean accepted = m.matches();

        if (!accepted) {
            FxUtilities.showInlineMessage(textFieldRoomName, FXMLConstants.MESSAGE_INVALID_ROOM_NAME);
            textFieldRoomName.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
        }

        return accepted;
    }
}
