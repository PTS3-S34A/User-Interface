/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import nl.soccar.gamecommuncation.util.ByteBufUtilities;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;
import nl.socnet.message.ChatMessage;

/**
 *
 * @author Raymond
 */
public final class ChatMessageHandler extends MessageHandler<ChatMessage> {

    @Override
    protected void handle(Connection connection, ChatMessage message) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void encode(Connection connection, ChatMessage message, ByteBuf buf) throws Exception {
        ByteBufUtilities.writeString(message.getMessage(), buf);
    }

    @Override
    protected ChatMessage decode(Connection connection, ByteBuf buf) throws Exception {
        String username = ByteBufUtilities.readString(buf);
        
        if (username == null) {
            return null;
        }
        
        String message = ByteBufUtilities.readString(buf);
        
        if (message == null) {
            return null;
        }
        
        return new ChatMessage(username, message);
    }

}
