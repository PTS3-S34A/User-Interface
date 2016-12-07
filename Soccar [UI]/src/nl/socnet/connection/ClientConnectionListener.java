package nl.socnet.connection;

import java.util.logging.Level;
import java.util.logging.Logger;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.connection.ConnectionListener;
import nl.soccar.ui.rmi.ClientController;

/**
 *
 * @author PTS34A
 */
public final class ClientConnectionListener implements ConnectionListener {
    
    private static final Logger LOGGER = Logger.getLogger(ClientConnectionListener.class.getSimpleName());

    @Override
    public void onConnected(Connection connection) {
        ClientController controller = ClientController.getInstance();
        connection.setPlayer(controller.getCurrentPlayer());
        controller.setCurrentConnection(connection);
        LOGGER.log(Level.INFO, "Connection made to Node with Ipaddress {0}", connection.getRemoteAddress().getAddress());
    }

    @Override
    public void onDisconnected(Connection connection) {
        // Do nothing on disconnect.
    }

}
