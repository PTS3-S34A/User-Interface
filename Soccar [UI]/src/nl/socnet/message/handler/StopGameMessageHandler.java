package nl.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import javafx.application.Platform;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;
import nl.soccar.ui.Main;
import nl.soccar.ui.fx.FXMLConstants;
import nl.soccar.ui.fx.controller.GameViewFXMLController;
import nl.socnet.message.StopGameMessage;

/**
 * Handler for StopGameMessage.
 *
 * @author PTS34A
 */
public class StopGameMessageHandler extends MessageHandler<StopGameMessage> {

    @Override
    protected void handle(Connection connection, StopGameMessage message) throws Exception {
        Object controller = Main.getInstance().getController();
        if (controller instanceof GameViewFXMLController) {
            GameViewFXMLController view = (GameViewFXMLController) controller;
            view.stop();

            Platform.runLater(() -> {
                Main main = Main.getInstance();
                main.setScene(FXMLConstants.LOCATION_GAME_RESULTS);
                main.setFullScreen(false);
            });
        }
    }

    @Override
    protected void encode(Connection connection, StopGameMessage message, ByteBuf buf) throws Exception {
        throw new UnsupportedOperationException("Encoding is not supported for Client.");
    }

    @Override
    protected StopGameMessage decode(Connection connection, ByteBuf buf) throws Exception {
        return new StopGameMessage();
    }

}
