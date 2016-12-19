package nl.soccar.ui;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import nl.soccar.library.Player;
import nl.soccar.library.enumeration.CarType;
import nl.soccar.library.enumeration.Privilege;
import nl.soccar.socnet.Client;
import nl.soccar.ui.rmi.ClientController;
import nl.soccar.ui.fx.FXMLConstants;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javafx.scene.control.Alert;
import nl.soccar.ui.util.FxUtilities;

/**
 * Entry point of the Soccar application. The Main class keeps track of the user
 * interface and provides a way to switch scenes.
 *
 * @author PTS34A
 */
public class Main extends Application {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getSimpleName());

    private static Main instance;

    private Stage primaryStage;

    /**
     * Constructor used for initiation of a Main object. This only happens once,
     * so the static instance gets filled out here.
     */
    public Main() {
        super();

        synchronized (Main.class) {
            if (instance != null) {
                throw new UnsupportedOperationException("Main is a singleton, cannot be called from its constructor.");
            }

            instance = this;
        }
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        primaryStage.setOnCloseRequest(e -> {
            Client client = ClientController.getInstance().getClient();

            if (client != null) {
                client.disconnect();
            }
        });

        setScene(FXMLConstants.LOCATION_LOGIN);
    }

    /**
     * Gets the instance of the Singleton Main class.
     *
     * @return The Singleton instance of the Main class.
     */
    public static Main getInstance() {
        return instance;
    }

    /**
     * Starts the application and configures the error logger.
     *
     * @param args The command line arguments to passed into the application.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Handles a loginOrRegister request. On success, it adjusts the current
     * Player and changes the scene to the register view.
     *
     * @param username The username of the Player.
     * @param selectedCar The selected car of the Player.
     */
    public void loginOrRegister(String username, CarType selectedCar) {
        ClientController.getInstance().setCurrentPlayer(new Player(username, Privilege.NORMAL, selectedCar));
        try {
            ClientController.getInstance().initialize();
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            FxUtilities.showAlert(FXMLConstants.ALERT_TITLE_CAR_NOT_SELECTED, FXMLConstants.ALERT_MESSAGE_CAR_NOT_SELECTED, Alert.AlertType.ERROR);
            setScene(FXMLConstants.LOCATION_LOGIN);
            return;
        }

        setScene(FXMLConstants.LOCATION_REGISTER);
    }

    /**
     * Handles a playAsGuest request. One success, it adjusts the current Player
     * and changes the scene to the main view.
     *
     * @param username The username of the Player.
     * @param selectedCar The selected car of the Player.
     */
    public void playAsGuest(String username, CarType selectedCar) {
        ClientController.getInstance().setCurrentPlayer(new Player(username, Privilege.GUEST, selectedCar));
        try {
            ClientController.getInstance().initialize();
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            FxUtilities.showAlert(FXMLConstants.ALERT_TITLE_CAR_NOT_SELECTED, FXMLConstants.ALERT_MESSAGE_CAR_NOT_SELECTED, Alert.AlertType.ERROR);
            setScene(FXMLConstants.LOCATION_LOGIN);
            return;
        }

        setScene(FXMLConstants.LOCATION_MAIN_MENU);
    }

    /**
     * Logs out the current user and changes the scene to the loginOrRegister
     * menu.
     */
    public void logOut() {
        ClientController controller = ClientController.getInstance();
        controller.setCurrentPlayer(null);

        Client client = controller.getClient();
        client.disconnect();

        setScene(FXMLConstants.LOCATION_LOGIN);
    }

    /**
     * Gets the controller of the current Scene.
     *
     * @return The controller of the current Scene.
     */
    public Object getController() {
        return primaryStage.getScene().getUserData();
    }

    /**
     * Changes the scene by giving a name. The actual file is resolved by
     * combining a predefined String and the given name. The stage title and
     * icon are also set.
     *
     * @param sceneName The name of the scene.
     */
    public void setScene(String sceneName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(sceneName));
            Parent root = loader.load();

            Scene scene = new Scene(root, DisplayConstants.SCREEN_WIDTH, DisplayConstants.SCREEN_HEIGHT);
            scene.setUserData(loader.getController());

            primaryStage.setTitle(DisplayConstants.APPLICATION_NAME);
            primaryStage.setScene(scene);
            primaryStage.getIcons().add(new Image(DisplayConstants.LOCATION_STAGE_ICON));

            primaryStage.show();
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "An error occurred while changing a scene.", e);
        }
    }

    /**
     * Sets the application window to full screen mode.
     *
     * @param fullScreen True if the application window needs to be set to full
     * screen mode.
     */
    public void setFullScreen(boolean fullScreen) {
        primaryStage.setMaximized(fullScreen);
        primaryStage.setFullScreen(fullScreen);
        primaryStage.setResizable(!fullScreen);
    }

    /**
     * Gets the width of the primary stage.
     *
     * @return The width of the primary stage.
     */
    public float getStageWidth() {
        return (float) primaryStage.getWidth();
    }

}
