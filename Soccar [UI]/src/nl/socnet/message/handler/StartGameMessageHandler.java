package nl.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;
import nl.socnet.message.StartGameMessage;

/**
 *
 * @author PTS34A
 */
public final class StartGameMessageHandler extends MessageHandler<StartGameMessage> {

    @Override
    protected void handle(Connection connection, StartGameMessage message) throws Exception {
        throw new UnsupportedOperationException("Handling is not supported for the Client.");
    }

    @Override
    protected void encode(Connection connection, StartGameMessage message, ByteBuf buf) throws Exception {
    }

    @Override
    protected StartGameMessage decode(Connection connection, ByteBuf buf) throws Exception {
        throw new UnsupportedOperationException("Decoding is not supported for the Client."); 
    }
    
}
