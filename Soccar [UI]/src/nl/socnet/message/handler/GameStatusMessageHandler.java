package nl.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import nl.soccar.library.enumeration.GameStatus;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;
import nl.soccar.ui.Main;
import nl.soccar.ui.fx.controller.SessionViewFXMLController;
import nl.socnet.message.GameStatusMessage;

/**
 * Handler for the GameStatusMessage class.
 *
 * @author PTS34A
 */
public final class GameStatusMessageHandler extends MessageHandler<GameStatusMessage> {

    @Override
    protected void handle(Connection connection, GameStatusMessage message) throws Exception {
        Object controller = Main.getInstance().getController();
        if (controller instanceof SessionViewFXMLController) {
            SessionViewFXMLController view = (SessionViewFXMLController) controller;
            view.setGameStatus(message.getGameStatus());
        }
    }

    @Override
    protected void encode(Connection connection, GameStatusMessage message, ByteBuf buf) throws Exception {
        throw new UnsupportedOperationException("Encoding not supported for Client.");
    }

    @Override
    protected GameStatusMessage decode(Connection connection, ByteBuf buf) throws Exception {
        if (buf.readableBytes() < 1) {
            buf.resetReaderIndex();
            return null;
        }

        GameStatus status = GameStatus.values()[buf.readByte()];
        return new GameStatusMessage(status);
    }

}
