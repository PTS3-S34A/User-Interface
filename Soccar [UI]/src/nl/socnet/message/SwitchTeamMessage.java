package nl.socnet.message;

import nl.soccar.library.enumeration.TeamColour;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageConstants;
import nl.soccar.socnet.message.MessageEvent;
import nl.socnet.message.handler.SwitchTeamMessageHandler;

/**
 * Message for switching to the other team.
 * 
 * @author PTS34A
 */
@MessageEvent(id = MessageConstants.SWITCH_TEAM_MESSAGE_ID, handler = SwitchTeamMessageHandler.class)
public final class SwitchTeamMessage extends Message {

    private final int playerId;
    private final TeamColour team;

    /**
     * Initializes a SwithTeam message.
     * 
     * @param playerId the playerId of the player that want to switch team, not null.
     * @param team the team that will be switched to, not null.
     */
    public SwitchTeamMessage(int playerId, TeamColour team) {
        this.playerId = playerId;
        this.team = team;
    }

    /**
     * Initializes a SwithTeam message.
     */
    public SwitchTeamMessage() {
        playerId = -1;
        team = null;
    }

    /**
     * Gets the playerId of the player that wants to switch teams.
     * 
     * @return int the PlayerId of the player.
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Gets the teamColour of the team that will be switched to.
     * 
     * @return TeamColour, the team that will be switched to.
     */
    public TeamColour getTeamColour() {
        return team;
    }

    @Override
    public int getId() {
        return MessageConstants.SWITCH_TEAM_MESSAGE_ID;
    }

}
