package nl.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;
import nl.socnet.message.StopGameMessage;

/**
 * Handler for StopGameMessage.
 *
 * @author PTS34A
 */
public class StopGameMessageHandler extends MessageHandler<StopGameMessage> {

    @Override
    protected void handle(Connection connection, StopGameMessage message) throws Exception {
        //TODO : Exactly stop the game.
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
