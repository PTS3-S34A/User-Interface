package nl.soccar.ui.rmi;

import nl.soccar.library.Player;
import nl.soccar.library.SessionData;
import nl.soccar.library.Statistics;
import nl.soccar.library.enumeration.BallType;
import nl.soccar.library.enumeration.Duration;
import nl.soccar.library.enumeration.MapType;
import nl.soccar.rmi.RmiConstants;
import nl.soccar.rmi.interfaces.IClientAuthenticated;
import nl.soccar.rmi.interfaces.IClientUnauthenticated;
import nl.soccar.socnet.Client;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageRegistry;
import nl.soccar.ui.util.PasswordUtilities;
import nl.socnet.connection.ClientConnectionListener;
import nl.socnet.message.*;

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

/**
 * Controller class that is responsible for handling RMI network communication
 * with the remote Main server.
 *
 * @author PTS34A
 */
public final class ClientController {

    private static final String LOCATION_PROPERTIES = "mainserver.properties";
    private static final Logger LOGGER = Logger.getLogger(ClientController.class.getSimpleName());

    private static final ClientController INSTANCE = new ClientController();
    private final Client client = new Client();
    private IClientUnauthenticated clientUnauthenticated;
    private IClientAuthenticated clientAuthenticated;
    private Connection currentConnection;
    private Player currentPlayer;

    /**
     * Constructor for instantiation of a ClientController object. The RMI
     * network connection is set up based on the IP-address of the Main server
     * that is supplied in the properties file.
     */
    private ClientController() {
        Properties props = new Properties();

        try (FileInputStream input = new FileInputStream(LOCATION_PROPERTIES)) {
            props.load(input);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "An error occurred while loading the mainserver properties file.", e);
        }

        try {
            Registry r = LocateRegistry.getRegistry(props.getProperty("mainserver"), RmiConstants.PORT_NUMBER_CLIENT);
            clientUnauthenticated = (IClientUnauthenticated) r.lookup(RmiConstants.BINDING_NAME_MAIN_SERVER_FOR_CLIENT);
        } catch (RemoteException | NotBoundException e) {
            LOGGER.log(Level.WARNING, "An error occurred while connecting to the Main server through RMI.", e);
        }

        initializeConnection();
    }

    /**
     * Gets the Singleton instance of the ClientController class.
     *
     * @return The Singleton instance of the ClientController class.
     */
    public static ClientController getInstance() {
        return INSTANCE;
    }

    private void initializeConnection() {
        MessageRegistry registry = client.getMessageRegistry();
        registry.register(RegisterPlayerMessage.class);

        registry.register(JoinSessionMessage.class);
        registry.register(LeaveSessionMessage.class);

        registry.register(PlayerJoinedSessionMessage.class);
        registry.register(PlayerLeftSessionMessage.class);

        registry.register(ChatMessage.class);
        registry.register(SwitchTeamMessage.class);

        registry.register(ChangeGameStatusMessage.class);
        registry.register(PlayerChangedGameStatusMessage.class);

        registry.register(MovePlayerMessage.class);
        registry.register(PlayerMovedMessage.class);

        registry.register(SpawnCarMessage.class);
        registry.register(SpawnObstacleMessage.class);
        registry.register(ChangePlayerStatusMessage.class);
        registry.register(PlayerSyncMessage.class);
        registry.register(BallSyncMessage.class);

        client.addListener(new ClientConnectionListener());

    }

    public boolean add(String username, String password) {
        try {
            return clientUnauthenticated.add(username, PasswordUtilities.generateHash(password));
        } catch (RemoteException e) {
            LOGGER.log(Level.WARNING, "An error occurred while adding a new user on the Main server through RMI.", e);
        }

        return false;
    }

    public boolean checkIfExists(String username) {
        try {
            return clientUnauthenticated.checkIfExists(username);
        } catch (RemoteException e) {
            LOGGER.log(Level.WARNING, "An error occurred while checking if a user exists on the Main server through RMI.", e);
        }

        return false;
    }

    public boolean checkPassword(String username, String password) {
        try {
            clientAuthenticated = clientUnauthenticated.checkPassword(username, PasswordUtilities.generateHash(password));
        } catch (RemoteException e) {
            LOGGER.log(Level.WARNING, "An error occurred while checking the users password on the Main server through RMI.", e);
        }

        return clientAuthenticated != null;
    }

    public boolean createSession(String name, String password, String hostName, int capacity, Duration duration, MapType mapType, BallType ballType) {
        try {
            return clientUnauthenticated.createSession(name, password, hostName, capacity, duration, mapType, ballType);
        } catch (RemoteException e) {
            LOGGER.log(Level.WARNING, "An error occurred while creating a session on the Main server through RMI.", e);
        }

        return false;
    }

    public List<SessionData> getAllSessions() {
        try {
            return clientUnauthenticated.getAllSessions();
        } catch (RemoteException e) {
            LOGGER.log(Level.WARNING, "An error occurred while retrieving all sessions from the Main server through RMI.", e);
        }

        return null;
    }

    public List<Statistics> getAllStatistics() {
        try {
            return clientUnauthenticated.getAllStatistics();
        } catch (RemoteException e) {
            LOGGER.log(Level.WARNING, "An error occurred while retrieving all statistics from the Main server through RMI.", e);
        }

        return null;
    }

    public Client getClient() {
        return client;
    }

    public Connection getCurrentConnection() {
        return currentConnection;
    }

    public void setCurrentConnection(Connection connection) {
        this.currentConnection = connection;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

}
