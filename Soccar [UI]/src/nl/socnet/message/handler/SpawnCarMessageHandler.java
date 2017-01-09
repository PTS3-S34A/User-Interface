package nl.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import nl.soccar.library.Car;
import nl.soccar.library.Player;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;
import nl.soccar.ui.Main;
import nl.soccar.ui.fx.controller.GameViewFXMLController;
import nl.soccar.ui.rmi.ClientController;
import nl.socnet.message.SpawnCarMessage;

import java.util.Optional;

/**
 * Handles SpawnCar messages, when cars get spawned.
 * 
 * @author PTS34A
 */
public final class SpawnCarMessageHandler extends MessageHandler<SpawnCarMessage> {

    @Override
    protected void handle(Connection connection, SpawnCarMessage message) throws Exception {
        Object controller = Main.getInstance().getController();
        if (controller instanceof GameViewFXMLController) {
            Optional<Player> optional = ClientController.getInstance().getCurrentPlayer()
                    .getCurrentSession().getRoom().getAllPlayers()
                    .stream().filter(p -> p.getPlayerId() == message.getPlayerId()).findFirst();
            if (!optional.isPresent()) {
                return;
            }

            Player player = optional.get();
            Car car = new Car(message.getX(), message.getY(), message.getAngle(), player.getCarType(), player);

            GameViewFXMLController view = (GameViewFXMLController) controller;
            view.spawnEntity(car);
        }
    }

    @Override
    protected void encode(Connection connection, SpawnCarMessage message, ByteBuf buf) throws Exception {
        throw new UnsupportedOperationException("decoding not supported for Client");
    }

    @Override
    protected SpawnCarMessage decode(Connection connection, ByteBuf buf) throws Exception {
        if (buf.readableBytes() < 1 + 3 * 4) {
            buf.resetReaderIndex();
            return null;
        }

        int id = buf.readByte();
        float x = buf.readFloat();
        float y = buf.readFloat();
        float angle = buf.readFloat();

        return new SpawnCarMessage(id, x, y, angle);
    }

}
