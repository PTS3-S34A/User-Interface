package nl.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import javafx.application.Platform;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;
import nl.soccar.ui.Main;
import nl.soccar.ui.fx.FXMLConstants;
import nl.soccar.ui.fx.controller.GameViewFXMLController;
import nl.socnet.message.ChangePlayerStatusMessage;
import nl.socnet.message.PlayerChangedGameStatusMessage;

import java.util.concurrent.FutureTask;

/**
 * @author PTS34A
 */
public final class PlayerChangedGameStatusMessageHandler extends MessageHandler<PlayerChangedGameStatusMessage> {

    @Override
    protected void handle(Connection connection, PlayerChangedGameStatusMessage message) throws Exception {
        Main main = Main.getInstance();
        if (!(main.getController() instanceof GameViewFXMLController)) {
            FutureTask<Void> task = new FutureTask<>(() -> {
                main.setScene(FXMLConstants.LOCATION_GAME_VIEW);
                main.setFullScreen(true);
                return null;
            });

            Platform.runLater(task);
            task.get();

            connection.send(new ChangePlayerStatusMessage(ChangePlayerStatusMessage.Status.REQUEST_WORLD_OBJECTS));
        }

        GameViewFXMLController view = (GameViewFXMLController) main.getController();
        PlayerChangedGameStatusMessage.Status status = message.getStatus();

        if (status == PlayerChangedGameStatusMessage.Status.GAME_PAUSED) {
            view.setPaused(true);
        } else if (status == PlayerChangedGameStatusMessage.Status.GAME_RUNNING) {
            view.setPaused(false);
        }

        // WILL BE UPDATED
    }

    @Override
    protected void encode(Connection connection, PlayerChangedGameStatusMessage message, ByteBuf buf) throws Exception {
        throw new UnsupportedOperationException("Encoding is not supported for the Client.");
    }

    @Override
    protected PlayerChangedGameStatusMessage decode(Connection connection, ByteBuf buf) throws Exception {
        if (buf.readableBytes() < 1) {
            buf.resetReaderIndex();
            return null;
        }

        PlayerChangedGameStatusMessage.Status status = PlayerChangedGameStatusMessage.Status.values()[buf.readByte()];
        return new PlayerChangedGameStatusMessage(status);
    }

}
