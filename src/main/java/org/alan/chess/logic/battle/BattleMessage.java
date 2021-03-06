package org.alan.chess.logic.battle;

import org.alan.chess.logic.battle.sprite.SpriteController;
import org.alan.chess.logic.constant.MessageConst;
import org.alan.chess.logic.sample.battle.CardSprite;
import org.alan.mars.protostuff.ProtobufMessage;
import org.alan.mars.protostuff.RequestMessage;
import org.alan.mars.protostuff.ResponseMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created on 2017/8/11.
 *
 * @author Alan
 * @since 1.0
 */
public interface BattleMessage {

    @ProtobufMessage
    class MoveChess {
        public BattlePoint fromPoint;
        public BattlePoint toPoint;
    }

    @ProtobufMessage(resp = true,messageType = MessageConst.Battle.TYPE, cmd = MessageConst.Battle.RESP_GAME_INIT)
    class RespGameInit {
        public int currentTeamId;
        public int mapId;
        public int battleSid;
        public Collection<CardSprite> allSprite;
        public List<TeamInfo> teamInfos;
    }

    @ProtobufMessage(resp = true,messageType = MessageConst.Battle.TYPE, cmd = MessageConst.Battle.RESP_MOVE_CHESS)
    class RespMoveChess {
        public long roleUid;
        public MoveChess moveChess;
    }



    @ProtobufMessage(resp = true,messageType = MessageConst.Battle.TYPE, cmd = MessageConst.Battle.RESP_CURRENT_GO_INFO)
    class RespCurrentGoInfo {
        public int countdownSecond;
        public int roundNum;
        public boolean newRound;
        public int currentTeamId;
        public long playerId;
    }

    static void sendGameInit(BattleController battleController) {
        RespGameInit respGameInit = new RespGameInit();
        respGameInit.currentTeamId = battleController.currentTeamId;
        respGameInit.battleSid = battleController.getSource().sid;
        respGameInit.mapId = battleController.getSource().mapId;
        respGameInit.teamInfos = Arrays.asList(battleController.teamInfos);
        SpriteController[][] sprites = battleController.pointSprites;
        respGameInit.allSprite = new ArrayList<>();
        for (int i = 0; i < sprites.length; i++) {
            for (int j = 0; j < sprites[i].length; j++) {
                SpriteController spc = sprites[i][j];
                if (spc != null) {
                    respGameInit.allSprite.add(spc.sprite);
                }
            }
        }
        battleController.broadcast(respGameInit);
    }

    static void sendCurrentGoInfo(BattleController battleController, boolean newRound) {
        RespCurrentGoInfo currentGoInfo = new RespCurrentGoInfo();
        currentGoInfo.currentTeamId = battleController.currentTeamId;
        currentGoInfo.playerId = battleController.currentPlayerId;
        currentGoInfo.roundNum = battleController.roundNum;
        currentGoInfo.newRound = newRound;
        currentGoInfo.countdownSecond = battleController.source.countdownSecond;
        battleController.broadcast(currentGoInfo);
    }

    static void sendMove(BattleController battleController, long playerId, MoveChess moveChess) {
        RespMoveChess respMoveChess = new RespMoveChess();
        respMoveChess.roleUid = playerId;
        respMoveChess.moveChess = moveChess;
        battleController.broadcast(respMoveChess);
    }

}
