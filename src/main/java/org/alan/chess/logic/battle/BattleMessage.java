package org.alan.chess.logic.battle;

import org.alan.mars.protostuff.RequestMessage;
import org.alan.mars.protostuff.ResponseMessage;

/**
 * Created on 2017/8/11.
 *
 * @author Alan
 * @since 1.0
 */
public interface BattleMessage {

    @RequestMessage
    class MoveChess {
        public int cardId;
        public int x;
        public int z;
    }

    @ResponseMessage(messageType = 1200, cmd = 1202)
    class RespMoveChess {
        public int roleUid;
        public MoveChess moveChess;
    }
}
