package org.alan.chess.logic.manager;

import org.alan.chess.logic.constant.MessageConst;
import org.alan.chess.logic.data.Player;
import org.alan.chess.logic.controller.PlayerController;
import org.alan.mars.protostuff.ProtobufMessage;
import org.springframework.stereotype.Component;

import static org.alan.chess.logic.constant.MessageConst.*;

/**
 * Created on 2017/8/4.
 *
 * @author Alan
 * @since 1.0
 */
@Component
public class LogicManager {

    @ProtobufMessage(resp = true, messageType = Login.TYPE, cmd = Login.RESP_ENTER_GAME)
    public static class EnterGame {
        public Player player;

        public EnterGame(Player player) {
            this.player = player;
        }
    }

    public void enterGame(PlayerController playerController) {
        playerController.sendToClient(new EnterGame(playerController.player));
    }
}
