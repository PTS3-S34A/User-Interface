package nl.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;
import nl.socnet.message.ChangeGameStatusMessage;

/**
 *
 * @author PTS34A
 */
public final class ChangeGameStatusMessageHandler extends MessageHandler<ChangeGameStatusMessage> {

    @Override
    protected void handle(Connection connection, ChangeGameStatusMessage message) throws Exception {
        throw new UnsupportedOperationException("Handling is not supported for the Client.");
    }

    @Override
    protected void encode(Connection connection, ChangeGameStatusMessage message, ByteBuf buf) throws Exception {
        // There is no data to encode for the message.
    }

    @Override
    protected ChangeGameStatusMessage decode(Connection connection, ByteBuf buf) throws Exception {
        throw new UnsupportedOperationException("Decoding is not supported for the Client.");
    }

}
