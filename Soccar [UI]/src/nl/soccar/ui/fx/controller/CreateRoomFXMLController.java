package nl.soccar.ui.fx.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import nl.soccar.library.GameSettings;
import nl.soccar.library.Player;
import nl.soccar.library.Session;
import nl.soccar.library.enumeration.BallType;
import nl.soccar.library.enumeration.Duration;
import nl.soccar.library.enumeration.MapType;
import nl.soccar.ui.rmi.ClientController;
import nl.soccar.ui.Main;
import nl.soccar.ui.fx.FXMLConstants;

/**
 * FXML Controller class
 *
 * @author PTS34A
 */
public class CreateRoomFXMLController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(CreateRoomFXMLController.class.getSimpleName());

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
        btnCreateRoom.setOnAction(e -> {
            if (!textFieldRoomName.getText().isEmpty()) {
                createRoom();
            } else {
                textFieldRoomName.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
            }
        });

        Player player = ClientController.getInstance().getCurrentPlayer();
        lblUsername.setText(player.getUsername());
        lblCar.setText(player.getCarType().toString());
        textFieldRoomName.setOnAction(e -> createRoom());

        ObservableList<MapType> maps = cbMap.getItems();
        maps.addAll(MapType.values());
        cbMap.setValue(MapType.GRASSLAND);

        ObservableList<BallType> balls = cbBall.getItems();
        balls.addAll(BallType.values());
        cbBall.setValue(BallType.FOOTBALL);
    }

    /**
     * Event-handler for CreatRoom button; Uses password, roomname, capacity and
     * map-type.
     */
    private void createRoom() {
        String roomName = textFieldRoomName.getText();
        String input = textFieldPassword.getText();
        String password = !input.isEmpty() ? input : "";
        int capacity = (int) sliderCapacity.getValue();
        Duration duration = Duration.values()[(int) sliderDuration.getValue() - 1]; 

        ClientController controller = ClientController.getInstance();

        if (!controller.createSession(roomName, password, capacity, 
                Duration.MINUTES_5, (MapType) cbMap.getValue(), BallType.FOOTBALL)) {
            return;
        }

        Session session = new Session(roomName, password);
        session.getRoom().setCapacity(capacity);

        GameSettings settings = session.getGame().getGameSettings();
        settings.setDuration(duration);
        settings.setMapType((MapType) cbMap.getValue());
        settings.setBallType((BallType) cbBall.getValue());

        controller.getCurrentPlayer().setCurrentSession(session);

        Main.getInstance().setScene(FXMLConstants.LOCATION_SESSION_VIEW);

        //TODO throw exception when room name alread exists
//        catch (DuplicateValueException e) {
//            LOGGER.log(Level.WARNING, "An error occurred while creating a room.", e);
//
//            Alert alert = new Alert(AlertType.WARNING);
//            alert.setTitle(e.getTitle());
//            alert.setHeaderText(e.getTitle());
//            alert.setContentText(e.getMessage());
//
//            alert.showAndWait();
//        }
    }

}
