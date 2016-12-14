package nl.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import javafx.application.Platform;
import nl.soccar.library.Player;
import nl.soccar.library.Room;
import nl.soccar.library.Session;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;
import nl.soccar.ui.Main;
import nl.soccar.ui.fx.controller.SessionViewFXMLController;
import nl.soccar.ui.rmi.ClientController;
import nl.socnet.message.PlayerLeftSessionMessage;

import java.util.Optional;

/**
 *
 * @author PTS34A
 */
public final class PlayerLeftSessionMessageHandler extends MessageHandler<PlayerLeftSessionMessage> {

    @Override
    protected void handle(Connection connection, PlayerLeftSessionMessage message) throws Exception {
        Session currentSession = ClientController.getInstance().getCurrentPlayer().getCurrentSession();

        Room room = currentSession.getRoom();
        Optional<Player> optional = room.getAllPlayers().stream().filter(p -> p.getPlayerId() == message.getPlayerId()).findFirst();
        if (!optional.isPresent()) {
            return;
        }

        Player player = optional.get();
        room.getTeamBlue().leave(player);
        room.getTeamRed().leave(player);

        Object controller = Main.getInstance().getController();
        if (controller != null && controller instanceof SessionViewFXMLController) {
            Platform.runLater(() -> ((SessionViewFXMLController) controller).setRoomInfo());
        }
    }

    @Override
    protected void encode(Connection connection, PlayerLeftSessionMessage message, ByteBuf buf) throws Exception {
        throw new UnsupportedOperationException("Encoding not supported for Client.");
    }

    @Override
    protected PlayerLeftSessionMessage decode(Connection connection, ByteBuf buf) throws Exception {
        if (buf.readableBytes() < 1) {
            buf.resetReaderIndex();
            return null;
        }

        int id = buf.readByte();
        return new PlayerLeftSessionMessage(id);
    }

}
