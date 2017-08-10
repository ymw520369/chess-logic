package org.alan.chess.logic.message;

import org.alan.chess.logic.constant.GameResultEnum;
import org.alan.mars.protostuff.PFSession;
import org.alan.mars.protostuff.ResponseMessage;

/**
 * 与客户端通信消息函数类
 * <p>
 * Created on 2017/8/4.
 *
 * @author Alan
 * @since 1.0
 */
public interface MessageToClient {

    @ResponseMessage(messageType = 1002, cmd = 1)
    class GameTips {
        public static final byte TIMER_TIPS = 1, CLOSED_TIPS = 2;
        public int tipsType;
        public GameResultEnum gameResultEnum;

        public GameTips(int tipsType, GameResultEnum gameResultEnum) {
            this.tipsType = tipsType;
            this.gameResultEnum = gameResultEnum;
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
