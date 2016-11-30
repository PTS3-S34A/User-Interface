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
    private ComboBox cbMap;

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

        ObservableList<MapType> list = cbMap.getItems();
        list.addAll(MapType.values());

        cbMap.setValue(MapType.GRASSLAND);
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

        ClientController controller = ClientController.getInstance();

        boolean success = controller.createSession(roomName, password, capacity,
                Duration.MINUTES_5, (MapType) cbMap.getValue(), BallType.FOOTBALL);
        if (!success) {
            System.err.println("it works not");
            return;
        }

        Session session = new Session(roomName, password);
        session.getRoom().setCapacity(capacity);
        
        GameSettings settings = session.getGame().getGameSettings();
        settings.setDuration(Duration.MINUTES_5); // TODO implement manual selection duration;
        settings.setMapType((MapType) cbMap.getValue());
        settings.setBallType(BallType.FOOTBALL);// TODO implement manual selection ball.
        
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
