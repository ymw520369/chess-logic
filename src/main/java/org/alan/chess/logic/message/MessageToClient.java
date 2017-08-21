package org.alan.chess.logic.message;

import org.alan.chess.logic.constant.GameResultEnum;
import org.alan.mars.protostuff.PFSession;
import org.alan.mars.protostuff.ProtobufMessage;

import static org.alan.chess.logic.constant.MessageCmdConst.TIPS_RESP_RESULT;
import static org.alan.chess.logic.constant.MessageTypeConst.*;

/**
 * 与客户端通信消息函数类
 * <p>
 * Created on 2017/8/4.
 *
 * @author Alan
 * @since 1.0
 */
public interface MessageToClient {

    @ProtobufMessage(resp = true, messageType = TIPS, cmd = TIPS_RESP_RESULT)
    class GameTips {
        public static final byte TIMER_TIPS = 1, CLOSED_TIPS = 2;
        public int tipsType;
        public int resultCode;
        public String resultDes;

        public GameTips(int tipsType, GameResultEnum gameResultEnum) {
            this.tipsType = tipsType;
            this.resultCode = gameResultEnum.code;
            resultDes=gameResultEnum.message;
        }
    }

    /**
     * 发送定时关闭的消息提示
     *
     * @param session
     * @param gameResultEnum
     */
    static void sendTimerGameTips(PFSession session, GameResultEnum gameResultEnum) {
        session.send(new GameTips(GameTips.TIMER_TIPS, gameResultEnum));
    }

    /**
     * 发送手动关闭的消息提示
     *
     * @param session
     * @param gameResultEnum
     */
    static void sendClosedGameTips(PFSession session, GameResultEnum gameResultEnum) {
        session.send(new GameTips(GameTips.CLOSED_TIPS, gameResultEnum));
    }
}
