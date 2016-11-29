package nl.soccar.ui.rmi;

import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.soccar.library.Player;
import nl.soccar.library.SessionData;
import nl.soccar.library.Statistics;
import nl.soccar.rmi.RmiConstants;
import nl.soccar.rmi.interfaces.IClientAuthenticated;
import nl.soccar.rmi.interfaces.IClientUnauthenticated;
import nl.soccar.ui.util.PasswordUtilities;

/**
 *
 * @author PTS34A
 */
public final class ClientController {

    private static final String LOCATION_PROPERTIES = "mainserver.properties";
    private static final Logger LOGGER = Logger.getLogger(ClientController.class.getSimpleName());

    private static final ClientController INSTANCE = new ClientController();
    
    private IClientUnauthenticated clientUnauthenticated;
    private IClientAuthenticated clientAuthenticated;
    
    private Player currentPlayer;

    private ClientController() {
        Properties props = new Properties();

        try (FileInputStream input = new FileInputStream(LOCATION_PROPERTIES)) {
            props.load(input);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "An error occurred while loading the mainserver properties file.", e);
        }

        try {
            Registry r = LocateRegistry.getRegistry(props.getProperty("mainserver", "localhost"), RmiConstants.PORT_NUMBER_CLIENT);
            clientUnauthenticated = (IClientUnauthenticated) r.lookup(RmiConstants.BINDING_NAME_MAIN_SERVER_FOR_CLIENT);
        } catch (RemoteException | NotBoundException e) {
            LOGGER.log(Level.WARNING, "An error occurred while connecting to the Main server through RMI.", e);
        }
    }

    public static ClientController getInstance() {
        return INSTANCE;
    }
    
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public boolean add(String username, String password) {
        try {
            return clientUnauthenticated.add(username, PasswordUtilities.generateHash(password));
        } catch (RemoteException e) {
            LOGGER.log(Level.WARNING, "An error occurred while adding a new user on the Main server through RMI.", e);
            return false;
        }
    }

    public boolean checkIfExists(String username) {
        try {
            return clientUnauthenticated.checkIfExists(username);
        } catch (RemoteException e) {
            LOGGER.log(Level.WARNING, "An error occurred while checking if a user exists on the Main server through RMI.", e);
            return false;
        }
    }

    public boolean checkPassword(String username, String password) {
        try {
            clientAuthenticated = clientUnauthenticated.checkPassword(username, PasswordUtilities.generateHash(password));
        } catch (RemoteException e) {
            LOGGER.log(Level.WARNING, "An error occurred while checking the users password on the Main server through RMI.", e);
        }
        return clientAuthenticated != null;
    }
    
    public List<SessionData> getAllSessions() {
        try {
            return clientUnauthenticated.getAllSessions();
        } catch (RemoteException e) {
            LOGGER.log(Level.WARNING, "An error occurred while retrieving all sessions from the Main server through RMI.", e);
            return null;
        }
    }

    public List<Statistics> getAllStatistics() {
        try {
            return clientUnauthenticated.getAllStatistics();
        } catch (RemoteException e) {
            LOGGER.log(Level.WARNING, "An error occurred while retrieving all statistics from the Main server through RMI.", e);
            return null;
        }
    }
}
