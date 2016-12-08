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
        ByteBufUtilities.writeString(message.getSteerAction().name(), buf);
        ByteBufUtilities.writeString(message.getHandbrakAction().name(), buf);
        ByteBufUtilities.writeString(message.getThrottleAction().name(), buf);
    }

    @Override
    protected PlayerMovedMessage decode(Connection connection, ByteBuf buf) throws Exception {
        throw new UnsupportedOperationException("Decoding is not supported for Client."); 
    }
    
}
