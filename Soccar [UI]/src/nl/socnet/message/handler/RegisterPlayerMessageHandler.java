package nl.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import nl.soccar.gamecommuncation.util.ByteBufUtilities;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;
import nl.socnet.message.RegisterPlayerMessage;

/**
 *
 * @author PTS34A
 */
public final class RegisterPlayerMessageHandler extends MessageHandler<RegisterPlayerMessage> {

    @Override
    protected void handle(Connection connection, RegisterPlayerMessage message) throws Exception {
        throw new UnsupportedOperationException("Handling not supported for Client.");
    }

    @Override
    protected void encode(Connection connection, RegisterPlayerMessage message, ByteBuf buf) throws Exception {
        ByteBufUtilities.writeString(message.getUsername(), buf);
        ByteBufUtilities.writeString(message.getCarType().name(), buf);
    }

    @Override
    protected RegisterPlayerMessage decode(Connection connection, ByteBuf buf) throws Exception {
        throw new UnsupportedOperationException("Decoding not supported for Client.");
    }
    
}
