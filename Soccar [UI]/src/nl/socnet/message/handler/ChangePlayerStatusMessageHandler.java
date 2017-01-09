package nl.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;
import nl.socnet.message.ChangePlayerStatusMessage;

/**
 * Handles ChangePlayerStatus messages, after the status of a player has changed.
 * 
 * @author PTS34A
 */
public final class ChangePlayerStatusMessageHandler extends MessageHandler<ChangePlayerStatusMessage> {

    @Override
    protected void handle(Connection connection, ChangePlayerStatusMessage message) throws Exception {
        if (message.getStatus() == ChangePlayerStatusMessage.Status.READY_TO_PLAY) {
            connection.send(new ChangePlayerStatusMessage(ChangePlayerStatusMessage.Status.READY_TO_PLAY));
        }
    }

    @Override
    protected void encode(Connection connection, ChangePlayerStatusMessage message, ByteBuf buf) throws Exception {
        buf.writeByte(message.getStatus().ordinal());
    }

    @Override
    protected ChangePlayerStatusMessage decode(Connection connection, ByteBuf buf) throws Exception {
        if (buf.readableBytes() < 1) {
            buf.resetReaderIndex();
            return null;
        }

        return new ChangePlayerStatusMessage(ChangePlayerStatusMessage.Status.values()[buf.readByte()]);
    }

}
