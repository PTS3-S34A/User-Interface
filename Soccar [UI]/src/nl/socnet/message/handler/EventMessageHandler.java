package nl.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import nl.soccar.library.*;
import nl.soccar.library.enumeration.EventType;
import nl.soccar.physics.GameEngine;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;
import nl.soccar.ui.Main;
import nl.soccar.ui.fx.controller.GameViewFXMLController;
import nl.soccar.ui.rmi.ClientController;
import nl.socnet.message.EventMessage;

import java.time.LocalTime;
import java.util.Optional;

/**
 * Handler for the EventMessage class.
 *
 * @author PTS34A
 */
public final class EventMessageHandler extends MessageHandler<EventMessage> {

    @Override
    protected void handle(Connection connection, EventMessage message) throws Exception {
        Session session = ClientController.getInstance().getCurrentPlayer().getCurrentSession();
        Room room = session.getRoom();
        Game game = session.getGame();

        Optional<Player> optional = room.getAllPlayers()
                .stream().filter(p -> p.getPlayerId() == message.getPlayerId()).findFirst();
        if (!optional.isPresent()) {
            return;
        }

        LocalTime time = LocalTime.now();

        Player player = optional.get();
        game.addEvent(new Event(message.getEventType(), time, player));

        Notification notification = game.getNotification();
        notification.setDisplayTime(time);
        notification.setPlayer(player);

        Object controller = Main.getInstance().getController();
        if (controller instanceof GameViewFXMLController) {
            GameViewFXMLController view = (GameViewFXMLController) controller;

            GameEngine engine = view.getGameCanvas().getGameEngine();
            engine.resetWorldObjects();
        }
    }

    @Override
    protected void encode(Connection connection, EventMessage message, ByteBuf buf) throws Exception {
        throw new UnsupportedOperationException("Encoding not supported for Client.");
    }

    @Override
    protected EventMessage decode(Connection connection, ByteBuf buf) throws Exception {
        if (buf.readableBytes() < 2) {
            buf.resetReaderIndex();
            return null;
        }

        EventType type = EventType.parse(buf.readByte());
        int playerId = buf.readByte();

        return new EventMessage(type, playerId);
    }

}
