package nl.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;
import nl.socnet.message.LeaveSessionMessage;

/**
 *
 * @author PTS34A
 */
public class LeaveSessionMessageHandler extends MessageHandler<LeaveSessionMessage> {

    @Override
    protected void handle(Connection connection, LeaveSessionMessage message) throws Exception {
        throw new UnsupportedOperationException("Handling is not supported for the Client.");
    }

    @Override
    protected void encode(Connection connection, LeaveSessionMessage message, ByteBuf buf) throws Exception {
        // There's nothing to encode.
    }

    @Override
    protected LeaveSessionMessage decode(Connection connection, ByteBuf buf) throws Exception {
        throw new UnsupportedOperationException("Decoding is not supported for the Client.");
    }

}
