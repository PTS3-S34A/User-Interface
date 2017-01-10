package nl.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import nl.soccar.library.Game;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;
import nl.soccar.ui.rmi.ClientController;
import nl.socnet.message.GameTimeSyncMessage;

/**
 * Handler for the GameTimeSyncMessage.
 *
 * @author PTS34A
 */
public class GameTimeSyncMessageHandler extends MessageHandler<GameTimeSyncMessage> {

    @Override
    protected void handle(Connection connection, GameTimeSyncMessage message) throws Exception {
        Game game = ClientController.getInstance().getCurrentPlayer().getCurrentSession().getGame();
        int currentGameTime = game.getSecondsLeft();

        long timeDifference = System.currentTimeMillis() - message.getCurrentTime();

        game.setGameTime(currentGameTime + (int) timeDifference);
    }

    @Override
    protected void encode(Connection connection, GameTimeSyncMessage message, ByteBuf buf) throws Exception {
        throw new UnsupportedOperationException("Encoding is not supported for Client.");
    }

    @Override
    protected GameTimeSyncMessage decode(Connection connection, ByteBuf buf) throws Exception {
        if (buf.readableBytes() < 12) {
            buf.resetReaderIndex();
            return null;
        }

        int gameTime = buf.readInt();
        long currentServerTime = buf.readLong();

        return new GameTimeSyncMessage(gameTime, currentServerTime);
    }

}
