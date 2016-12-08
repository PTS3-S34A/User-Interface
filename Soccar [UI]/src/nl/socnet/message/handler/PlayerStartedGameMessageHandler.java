package nl.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import nl.soccar.library.Game;
import nl.soccar.library.Player;
import nl.soccar.library.Session;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;
import nl.soccar.ui.Main;
import nl.soccar.ui.fx.FXMLConstants;
import nl.soccar.ui.rmi.ClientController;
import nl.socnet.message.PlayerStartedGameMessage;

/**
 *
 * @author PTS34A
 */
public final class PlayerStartedGameMessageHandler extends MessageHandler<PlayerStartedGameMessage> {

    private static final Logger LOGGER = Logger.getLogger(PlayerStartedGameMessageHandler.class.getSimpleName());

    @Override
    protected void handle(Connection connection, PlayerStartedGameMessage message) throws Exception {
        Player player = ClientController.getInstance().getCurrentPlayer();
        Session session = player.getCurrentSession();
        Game game = session.getGame();
        game.start();

        Platform.runLater(() -> {
            Main main = Main.getInstance();
            main.setScene(FXMLConstants.LOCATION_GAME_VIEW);
            main.setFullScreen(true);

        });
        LOGGER.log(Level.INFO, "Started Game.");

    }

    @Override
    protected void encode(Connection connection, PlayerStartedGameMessage message, ByteBuf buf) throws Exception {
        throw new UnsupportedOperationException("Encoding is not supported for the Client.");
    }

    @Override
    protected PlayerStartedGameMessage decode(Connection connection, ByteBuf buf) throws Exception {
        return new PlayerStartedGameMessage();
    }

}