package org.alan.chess.logic.match;

import org.alan.chess.logic.battle.PlayerFighter;
import org.alan.chess.logic.room.RoomController;
import org.alan.chess.logic.room.RoomSit;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2017/8/10.
 *
 * @author Alan
 * @since 1.0
 */
public class MatchInfo {
    public long beginTime;
    public int roomId;
    public RoomSit[] roomSits;

    public MatchInfo(long beginTime, RoomController roomController) {
        this.beginTime = beginTime;
        this.roomId = roomController.uid;
        this.roomSits = roomController.roomSits;
    }

    public boolean match(MatchInfo matchInfo) {
        //TODO
        return true;
    }

    public TeamInfo getTeamInfo() {
        TeamInfo teamInfo = new TeamInfo(roomId, getFighters());
        return teamInfo;
    }

    private Map<Long, PlayerFighter> getFighters() {
        Map<Long, PlayerFighter> fighters = new HashMap<>();
        for (RoomSit sit : roomSits) {
            if (sit.playerController != null) {
                fighters.put(sit.playerController.playerId(), new PlayerFighter(sit.playerController));
            }
        }
        return fighters;
    }
}
