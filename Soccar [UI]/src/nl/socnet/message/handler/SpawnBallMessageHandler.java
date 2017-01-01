package nl.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import nl.soccar.library.Ball;
import nl.soccar.library.enumeration.BallType;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;
import nl.soccar.ui.Main;
import nl.soccar.ui.fx.controller.GameViewFXMLController;
import nl.socnet.message.SpawnBallMessage;

/**
 * @author PTS34A
 */
public final class SpawnBallMessageHandler extends MessageHandler<SpawnBallMessage> {

    @Override
    protected void handle(Connection connection, SpawnBallMessage message) throws Exception {
        Object controller = Main.getInstance().getController();
        if (controller instanceof GameViewFXMLController) {
            Ball ball = new Ball(message.getX(), message.getY(), message.getAngle(), message.getBallType());

            GameViewFXMLController view = (GameViewFXMLController) controller;
            view.spawnEntity(ball);
        }
    }

    @Override
    protected void encode(Connection connection, SpawnBallMessage message, ByteBuf buf) throws Exception {
        throw new UnsupportedOperationException("decoding not supported for Client");
    }

    @Override
    protected SpawnBallMessage decode(Connection connection, ByteBuf buf) throws Exception {
        if (buf.readableBytes() < 1 + 3 * 4) {
            buf.resetReaderIndex();
            return null;
        }

        float x = buf.readFloat();
        float y = buf.readFloat();
        float angle = buf.readFloat();
        BallType type = BallType.parse(buf.readByte());

        return new SpawnBallMessage(x, y, angle, type);
    }

}
