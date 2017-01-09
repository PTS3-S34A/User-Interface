package nl.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import nl.soccar.library.Player;
import nl.soccar.library.Session;
import nl.soccar.physics.GameEngine;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;
import nl.soccar.ui.Main;
import nl.soccar.ui.fx.controller.GameViewFXMLController;
import nl.soccar.ui.rmi.ClientController;
import nl.socnet.message.PlayerSyncMessage;

import java.util.Optional;

/**
 * Handles PlayerSync messages.
 * 
 * @author PTS34A
 */
public final class PlayerSyncMessageHandler extends MessageHandler<PlayerSyncMessage> {

    @Override
    protected void handle(Connection connection, PlayerSyncMessage message) throws Exception {
        Player player = ClientController.getInstance().getCurrentPlayer();
        Session session = player.getCurrentSession();

        Optional<Player> optional = session.getRoom().getAllPlayers().stream().filter(p -> p.getPlayerId() == message.getPlayerId()).findFirst();
        if (!optional.isPresent()) {
            return;
        }

        Player other = optional.get();

        Object controller = Main.getInstance().getController();
        if (controller instanceof GameViewFXMLController) {
            GameViewFXMLController view = (GameViewFXMLController) controller;

            GameEngine engine = view.getGameCanvas().getGameEngine();
            engine.getCarFromPlayer(other).setPosition(message.getX(), message.getY(), message.getAngle(), message.getLinearVelocityX(), message.getLinearVelocityY(), message.getAngularVelocity());
        }
    }

    @Override
    protected void encode(Connection connection, PlayerSyncMessage message, ByteBuf buf) throws Exception {
        throw new UnsupportedOperationException("encoding not supported for Client");
    }

    @Override
    protected PlayerSyncMessage decode(Connection connection, ByteBuf buf) throws Exception {
        if (buf.readableBytes() < 1 + 6 * 4) {
            buf.resetReaderIndex();
            return null;
        }

        int id = buf.readByte();

        float x = buf.readFloat();
        float y = buf.readFloat();
        float angle = buf.readFloat();

        float linearVelocityX = buf.readFloat();
        float linearVelocityY = buf.readFloat();
        float angularVelocity = buf.readFloat();

        return new PlayerSyncMessage(id, x, y, angle, linearVelocityX, linearVelocityY, angularVelocity);
    }

}
