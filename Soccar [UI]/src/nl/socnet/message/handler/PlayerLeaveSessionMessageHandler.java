package nl.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import nl.soccar.gamecommuncation.util.ByteBufUtilities;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;
import nl.socnet.message.PlayerLeaveSessionMessage;

/**
 *
 * @author PTS34A
 */
public class PlayerLeaveSessionMessageHandler extends MessageHandler<PlayerLeaveSessionMessage> {

    @Override
    protected void handle(Connection connection, PlayerLeaveSessionMessage message) throws Exception {
        throw new UnsupportedOperationException("Handling is not supported for the Client.");
    }

    @Override
    protected void encode(Connection connection, PlayerLeaveSessionMessage message, ByteBuf buf) throws Exception {
        ByteBufUtilities.writeString(message.getUsername(), buf);
        buf.writeByte(message.getTeamColour().getId());
    }

    @Override
    protected PlayerLeaveSessionMessage decode(Connection connection, ByteBuf buf) throws Exception {
        throw new UnsupportedOperationException("Decoding is not supported for the Client.");
    }

}
