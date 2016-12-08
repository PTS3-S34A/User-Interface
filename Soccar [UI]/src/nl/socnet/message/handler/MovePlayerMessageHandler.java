package nl.socnet.message.handler;

import io.netty.buffer.ByteBuf;
import java.util.List;
import nl.soccar.gamecommuncation.util.ByteBufUtilities;
import nl.soccar.library.Car;
import nl.soccar.library.Map;
import nl.soccar.library.Player;
import nl.soccar.library.Session;
import nl.soccar.library.enumeration.HandbrakeAction;
import nl.soccar.library.enumeration.SteerAction;
import nl.soccar.library.enumeration.ThrottleAction;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;
import nl.soccar.ui.rmi.ClientController;
import nl.socnet.message.MovePlayerMessage;

/**
 *
 * @author PTS34A
 */
public final class MovePlayerMessageHandler extends MessageHandler<MovePlayerMessage> {

    @Override
    protected void handle(Connection connection, MovePlayerMessage message) throws Exception {
        Player player = ClientController.getInstance().getCurrentPlayer();
        Session currentSession = player.getCurrentSession();
        
        Map map = currentSession.getGame().getMap();
        List<Player> players = currentSession.getRoom().getAllPlayers();
        
        Car car = players.stream().filter(p -> p.getUsername().equals(message.getUsername())).map(map::getCarFromPlayer).findFirst().get();
        car.setHandbrakeAction(message.getHandbrakAction());
        car.setSteerAction(message.getSteerAction());
        car.setThrottleAction(message.getThrottleAction());
    }

    @Override
    protected void encode(Connection connection, MovePlayerMessage message, ByteBuf buf) throws Exception {
        throw new UnsupportedOperationException("Encoding is not supported for the Client.");
    }

    @Override
    protected MovePlayerMessage decode(Connection connection, ByteBuf buf) throws Exception {
        String username = ByteBufUtilities.readString(buf);
        SteerAction steerAction = SteerAction.parse(buf.readByte());
        HandbrakeAction handbrakeAction = HandbrakeAction.parse(buf.readByte());
        ThrottleAction throttleAction = ThrottleAction.parse(buf.readByte());
        
        return new MovePlayerMessage(username, steerAction, handbrakeAction, throttleAction);
    }
    
}
