package nl.soccar.ui.fx;

/**
 * This class keeps track of all constants regarding JavaFX. For example the
 * locations of FXML files.
 *
 * @author PTS34A
 */
public final class FXMLConstants {

    // Locations of FXML files.
    public static final String LOCATION_MAIN_MENU = "resources/fxml/MainMenuFXML.fxml";
    public static final String LOCATION_LOGIN = "resources/fxml/LoginFXML.fxml";
    public static final String LOCATION_CREATE_ROOM = "resources/fxml/CreateRoomFXML.fxml";
    public static final String LOCATION_SESSION_VIEW = "resources/fxml/SessionViewFXML.fxml";
    public static final String LOCATION_GAME_VIEW = "resources/fxml/GameViewFXML.fxml";
    public static final String LOCATION_GAME_RESULTS = "resources/fxml/GameResultsFXML.fxml";
    public static final String LOCATION_REGISTER = "resources/fxml/RegisterFXML.fxml";
    public static final String LOCATION_CONTROLS = "resources/fxml/GameControlsFXML.fxml";

    // GUI warnings & description
    public static final String WARNING_USERNAME_EXISTS = "USERNAME EXISTS";
    public static final String WARNING_NEW_USERNAME = "REGISTER USERNAME";
    public static final String MESSAGE_USERNAME_EXISTS = "Username is already registered.";
    public static final String MESSAGE_NEW_USERNAME = "REGISTER";
    public static final String MESSAGE_INVALID_USERNAME = "A username can only contain alphanumeric characters & can't contain more than 16 characters.";
    public static final String MESSAGE_INVALID_USER_CREDENTIALS = "A password needs to be at least 8 characters long.";
    public static final String MESSAGE_FALSE_USER_CREDENTIALS = "The credentials you've provided seem to be incorrect.";
    public static final String MESSAGE_INVALID_ROOM_NAME = "A roomname can only contain alphanumeric characters & can't contain more than 16 characters.";
    public static final String MESSAGE_FALSE_ROOM_CREDENTIALS = "The password you've provided seems to be incorrect.";
    public static final String MESSAGE_CAR_NOT_SELECTED = "You'll need to select a car.";

    // Alert titles & messages
    public static final String ALERT_TITLE_NO_CONNECTION = "Couldn't connect to the server.";
    public static final String ALERT_MESSAGE_NO_CONNECTION = "Did a mice chew on your internetcable?";

    private FXMLConstants() {
    }

}
