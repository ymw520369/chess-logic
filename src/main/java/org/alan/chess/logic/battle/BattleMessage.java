package org.alan.chess.logic.battle;

import org.alan.chess.logic.sample.battle.CardSprite;
import org.alan.mars.protostuff.RequestMessage;
import org.alan.mars.protostuff.ResponseMessage;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on 2017/8/11.
 *
 * @author Alan
 * @since 1.0
 */
public interface BattleMessage {

    @RequestMessage
    class MoveChess {
        public BattlePoint fromPoint;
        public BattlePoint toPoint;
    }

    @ResponseMessage(messageType = 1200, cmd = 2)
    class RespMoveChess {
        public int roleUid;
        public MoveChess moveChess;
    }

    @ResponseMessage(messageType = 1200, cmd = 4)
    class RespGameInit {
        public int mapId;
        public int battleSid;
        public Collection<CardSprite> allSprite;
        public Collection<PlayerFighter> fighters;
    }

    @ResponseMessage(messageType = 1200, cmd = 6)
    class RespCurrentGoInfo {
        public int roundNum;
        public boolean newRound;
        public int currentTeamId;
        public long playerId;
    }

    static void sendGameInit(BattleController battleController) {
        RespGameInit respGameInit = new RespGameInit();
        respGameInit.battleSid = battleController.getSource().sid;
        respGameInit.mapId = battleController.getSource().mapId;
        respGameInit.fighters = battleController.fighters.values();
        respGameInit.allSprite = battleController.spriteMap.values().stream().map(sc -> sc.sprite).collect(Collectors.toList());
        battleController.broadcast(respGameInit);
    }

    static void sendCurrentGoInfo(BattleController battleController, boolean newRound) {
        RespCurrentGoInfo currentGoInfo = new RespCurrentGoInfo();
        currentGoInfo.currentTeamId = battleController.currentTeamId;
        currentGoInfo.playerId = battleController.currentPlayerId;
        currentGoInfo.roundNum = battleController.roundNum;
        currentGoInfo.newRound = newRound;
        battleController.broadcast(currentGoInfo);
    }

    static void broadcastGameInput(BattleController battleController) {

    }
}
