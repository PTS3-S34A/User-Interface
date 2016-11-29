package nl.soccar.ui.fx.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import nl.soccar.exception.DuplicateValueException;
import nl.soccar.library.Game;
import nl.soccar.library.GameSettings;
import nl.soccar.library.Player;
import nl.soccar.library.Session;
import nl.soccar.library.enumeration.BallType;
import nl.soccar.library.enumeration.Duration;
import nl.soccar.library.enumeration.MapType;
import nl.soccar.rmi.ClientController;
import nl.soccar.ui.DisplayConstants;
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
//        String password = "";
//
//        if (!textFieldPassword.getText().isEmpty()) {
//            password = textFieldPassword.getText();
//        }
//
//        Session session;
//        try {
//            Soccar soccar = Soccar.getInstance();
//            SessionController controller = SessionController.getInstance();
//
//            session = controller.create(textFieldRoomName.getText(), password, soccar.getCurrentPlayer());
//            session.getRoom().setCapacity((int) sliderCapacity.getValue());
//
//            Game game = new Game.Builder().setMapWidth(DisplayConstants.MAP_WIDTH)
//                    .setMapHeight(DisplayConstants.MAP_HEIGHT)
//                    .setGoalWidth(DisplayConstants.GOAL_WIDTH)
//                    .setGoalHeight(DisplayConstants.GOAL_HEIGHT)
//                    .setBallRadius(DisplayConstants.BALL_RADIUS)
//                    .setFieldMargin(DisplayConstants.FIELD_MARGIN).build();
//            GameSettings settings = game.getGameSettings();
//            
//            settings.setDuration(Duration.MINUTES_5); // TODO implement manual selection duration;
//            settings.setMapType((MapType) cbMap.getValue());
//            settings.setBallType(BallType.FOOTBALL);// TODO implement manual selection ball.
//
//            session.setGame(game);
//            soccar.getCurrentPlayer().setCurrentSession(session);
//            
//            Main.getInstance().setScene(FXMLConstants.LOCATION_SESSION_VIEW);
//        } catch (DuplicateValueException e) {
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
