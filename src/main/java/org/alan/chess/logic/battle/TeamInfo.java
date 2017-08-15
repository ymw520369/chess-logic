package org.alan.chess.logic.battle;

import org.alan.chess.logic.battle.PlayerFighter;
import org.alan.mars.protostuff.ProtobufMessage;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created on 2017/8/14.
 *
 * @author Alan
 * @since 1.0
 */
@ProtobufMessage
public class TeamInfo {
    public int teamId;
    public int roomId;
    private List<PlayerFighter> fighters;
//    public PlayerFighter[] fighters;

    public TeamInfo(int roomId, PlayerFighter[] fighters) {
        this.roomId = roomId;
        this.fighters = Arrays.asList(fighters);
    }

    public boolean battleInitDone() {
        return fighters.stream().allMatch(playerFighter -> playerFighter.initDone);
    }

    public PlayerFighter getFighter(int index){
        return fighters.get(index);
    }
    public PlayerFighter fighter(){
        return fighters.get(0);
    }
}
