package nl.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import nl.soccar.gamecommuncation.util.ByteBufUtilities;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;
import nl.socnet.message.PlayerMovedMessage;

/**
 *
 * @author PTS34A
 */
public final class PlayerMovedMessageHandler extends MessageHandler<PlayerMovedMessage> {

    @Override
    protected void handle(Connection connection, PlayerMovedMessage message) throws Exception {
        throw new UnsupportedOperationException("Handling is not supported for Client.");
    }

    @Override
    protected void encode(Connection connection, PlayerMovedMessage message, ByteBuf buf) throws Exception {
        buf.writeByte(message.getSteerAction().getId());
        buf.writeByte(message.getHandbrakAction().getId());
        buf.writeByte(message.getThrottleAction().getId());
    }

    @Override
    protected PlayerMovedMessage decode(Connection connection, ByteBuf buf) throws Exception {
        throw new UnsupportedOperationException("Decoding is not supported for Client."); 
    }
    
}
