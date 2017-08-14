package org.alan.chess.logic.match;

import org.alan.chess.logic.battle.PlayerFighter;

import java.util.Map;

/**
 * Created on 2017/8/14.
 *
 * @author Alan
 * @since 1.0
 */
public class TeamInfo {
    public int teamId;
    public int roomId;
    public Map<Long, PlayerFighter> fighters;

    public TeamInfo(int roomId, Map<Long, PlayerFighter> fighters) {
        this.roomId = roomId;
        this.fighters = fighters;
    }

    public boolean battleInitDone() {
        return fighters.values().stream().allMatch(playerFighter -> playerFighter.initDone);
    }
}
