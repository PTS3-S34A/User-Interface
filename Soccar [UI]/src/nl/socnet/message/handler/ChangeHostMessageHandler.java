package nl.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import javafx.application.Platform;
import nl.soccar.library.Player;
import nl.soccar.library.Room;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;
import nl.soccar.ui.Main;
import nl.soccar.ui.fx.controller.SessionViewFXMLController;
import nl.soccar.ui.rmi.ClientController;
import nl.socnet.message.ChangeHostMessage;

import java.util.Optional;

/**
 * Handler for the ChangeHostMessage class.
 *
 * @author PTS34A
 */
public final class ChangeHostMessageHandler extends MessageHandler<ChangeHostMessage> {

    @Override
    protected void handle(Connection connection, ChangeHostMessage message) throws Exception {
        Room room = ClientController.getInstance().getCurrentPlayer().getCurrentSession().getRoom();

        int id = message.getPlayerId();
        if (id == -1) {
            room.setHost(null);
        } else {
            Optional<Player> optional = room.getAllPlayers().stream().filter(p -> p.getPlayerId() == message.getPlayerId()).findFirst();
            if (!optional.isPresent()) {
                return;
            }

            room.setHost(optional.get());
        }

        Object controller = Main.getInstance().getController();
        if (controller != null && controller instanceof SessionViewFXMLController) {
            SessionViewFXMLController view = (SessionViewFXMLController) controller;
            Platform.runLater(view::setRoomInfo);
        }
    }

    @Override
    protected void encode(Connection connection, ChangeHostMessage message, ByteBuf buf) throws Exception {
        throw new UnsupportedOperationException("Decoding not supported for Client.");
    }

    @Override
    protected ChangeHostMessage decode(Connection connection, ByteBuf buf) throws Exception {
        if (buf.readableBytes() < 1) {
            buf.resetReaderIndex();
            return null;
        }

        return new ChangeHostMessage(buf.readByte());
    }

}
