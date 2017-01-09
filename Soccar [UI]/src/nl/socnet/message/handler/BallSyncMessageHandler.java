package nl.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import nl.soccar.physics.GameEngine;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;
import nl.soccar.ui.Main;
import nl.soccar.ui.fx.controller.GameViewFXMLController;
import nl.socnet.message.BallSyncMessage;

/**
 * Handles BallSync messages, to synchronize the ball across all clients.
 * 
 * @author PTS34A
 */
public final class BallSyncMessageHandler extends MessageHandler<BallSyncMessage> {

    @Override
    protected void handle(Connection connection, BallSyncMessage message) throws Exception {
        Object controller = Main.getInstance().getController();
        if (controller instanceof GameViewFXMLController) {
            GameViewFXMLController view = (GameViewFXMLController) controller;

            GameEngine engine = view.getGameCanvas().getGameEngine();
            engine.getBall().setPosition(message.getX(), message.getY(), -1, message.getLinearVelocityX(), message.getLinearVelocityY(), message.getAngularVelocity());
        }
    }

    @Override
    protected void encode(Connection connection, BallSyncMessage message, ByteBuf buf) throws Exception {
        throw new UnsupportedOperationException("encoding not supported for Client");
    }

    @Override
    protected BallSyncMessage decode(Connection connection, ByteBuf buf) throws Exception {
        if (buf.readableBytes() < 5 * 4) {
            buf.resetReaderIndex();
            return null;
        }

        float x = buf.readFloat();
        float y = buf.readFloat();

        float linearVelocityX = buf.readFloat();
        float linearVelocityY = buf.readFloat();
        float angularVelocity = buf.readFloat();

        return new BallSyncMessage(x, y, linearVelocityX, linearVelocityY, angularVelocity);
    }

}
