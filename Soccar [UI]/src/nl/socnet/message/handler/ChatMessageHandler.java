package nl.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import javafx.application.Platform;
import nl.soccar.gamecommuncation.util.ByteBufUtilities;
import nl.soccar.library.Player;
import nl.soccar.library.enumeration.Privilege;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;
import nl.soccar.ui.Main;
import nl.soccar.ui.fx.controller.SessionViewFXMLController;
import nl.soccar.ui.rmi.ClientController;
import nl.socnet.message.ChatMessage;

import java.util.Optional;

/**
 *
 * @author PTS34A
 */
public final class ChatMessageHandler extends MessageHandler<ChatMessage> {

    @Override
    protected void handle(Connection connection, ChatMessage message) throws Exception {
        Object controller = Main.getInstance().getController();
        if (controller != null && controller instanceof SessionViewFXMLController) {
            Optional<Player> optional = ClientController.getInstance().getCurrentPlayer().getCurrentSession().getRoom().getAllPlayers().stream().filter(p -> p.getPlayerId() == message.getPlayerId()).findFirst();
            if (!optional.isPresent()) {
                return;
            }

            SessionViewFXMLController view = (SessionViewFXMLController) controller;
            Platform.runLater(() -> view.addChatMessage(optional.get().getUsername(), message.getPrivilege(), message.getMessage()));
        }
    }

    @Override
    protected void encode(Connection connection, ChatMessage message, ByteBuf buf) throws Exception {
        ByteBufUtilities.writeString(message.getMessage(), buf);
    }

    @Override
    protected ChatMessage decode(Connection connection, ByteBuf buf) throws Exception {
        if (buf.readableBytes() < 2) {
            buf.resetReaderIndex();
            return null;
        }

        int id = buf.readByte();
        Privilege privilege = Privilege.parse(buf.readByte());

        String message = ByteBufUtilities.readString(buf);
        if (message == null) {
            return null;
        }

        return new ChatMessage(id, privilege, message);
    }

}
