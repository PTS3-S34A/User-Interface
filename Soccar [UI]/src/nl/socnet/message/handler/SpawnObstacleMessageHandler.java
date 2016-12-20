package nl.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import nl.soccar.library.Obstacle;
import nl.soccar.library.Player;
import nl.soccar.library.enumeration.ObstacleType;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;
import nl.soccar.ui.Main;
import nl.soccar.ui.fx.controller.GameViewFXMLController;
import nl.soccar.ui.rmi.ClientController;
import nl.socnet.message.SpawnObstacleMessage;

/**
 * @author PTS34A
 */
public final class SpawnObstacleMessageHandler extends MessageHandler<SpawnObstacleMessage> {

    @Override
    protected void handle(Connection connection, SpawnObstacleMessage message) throws Exception {
        Object controller = Main.getInstance().getController();
        if (controller instanceof GameViewFXMLController) {
            Player player = ClientController.getInstance().getCurrentPlayer();
            Obstacle obstacle = new Obstacle(message.getX(), message.getY(), message.getAngle(), message.getWidth(), message.getHeight(), message.getObstacleType());

            GameViewFXMLController view = (GameViewFXMLController) controller;
            view.spawnEntity(obstacle);
        }
    }

    @Override
    protected void encode(Connection connection, SpawnObstacleMessage message, ByteBuf buf) throws Exception {
        throw new UnsupportedOperationException("decoding not supported for Client");
    }

    @Override
    protected SpawnObstacleMessage decode(Connection connection, ByteBuf buf) throws Exception {
        if (buf.readableBytes() < 1 + 5 * 4) {
            buf.resetReaderIndex();
            return null;
        }

        float x = buf.readFloat();
        float y = buf.readFloat();
        float angle = buf.readFloat();
        float width = buf.readFloat();
        float height = buf.readFloat();
        ObstacleType type = ObstacleType.parse(buf.readByte());

        return new SpawnObstacleMessage(x, y, angle, width, height, type);
    }

}
