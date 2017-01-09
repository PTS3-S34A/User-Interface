package nl.socnet.message;

import nl.soccar.library.enumeration.CarType;
import nl.soccar.library.enumeration.Privilege;
import nl.soccar.library.enumeration.TeamColour;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;
import nl.socnet.message.handler.PlayerJoinedSessionMessageHandler;

/**
 * Message for a Player joining.
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.PLAYER_JOINED_SESSION_MESSAGE_ID, handler = PlayerJoinedSessionMessageHandler.class)
public final class PlayerJoinedSessionMessage extends Message {

    private final int playerId;
    private final String username;
    private final Privilege privilege;

    private final CarType carType;
    private final TeamColour team;

    /**
     * Initializes a PlayerJoinedSession message.
     * 
     * @param playerId the Id of the joinging Player, not null.
     * @param username the Username of the joining Player, not null.
     * @param privilege the Privilage of the joinging Player, not null.
     * @param carType the carType of the joining Player, not null.
     * @param team the team of the joining Player, not null.
     */
    public PlayerJoinedSessionMessage(int playerId, String username, Privilege privilege, CarType carType, TeamColour team) {
        this.playerId = playerId;
        this.username = username;
        this.privilege = privilege;

        this.carType = carType;
        this.team = team;
    }

    /**
     * Gets the playerId of the joined player.
     * 
     * @return int PlayerId of the joined player.
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Gets the username of the joined player.
     * 
     * @return String the username of the joined player. 
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the privilege of the joined player.
     * 
     * @return Privilege the privilege of the joined player.
     */
    public Privilege getPrivilege() {
        return privilege;
    }

    /**
     * Gets the carType of the joined player.
     * 
     * @return CarType the carType of the joined player. 
     */
    public CarType getCarType() {
        return carType;
    }

    /**
     * Gets the teamColour of the joined player.
     * 
     * @return TeamColour the teamColour of the joined player.
     */
    public TeamColour getTeamColour() {
        return team;
    }

    @Override
    public int getId() {
        return MessageConstants.PLAYER_JOINED_SESSION_MESSAGE_ID;
    }

}
