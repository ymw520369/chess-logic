package org.alan.chess.logic.manager;

import org.alan.chess.logic.data.Player;
import org.alan.chess.logic.data.Role;
import org.alan.chess.logic.data.UserInfo;
import org.alan.chess.logic.controller.PlayerController;
import org.alan.mars.protostuff.PFSession;
import org.alan.mars.protostuff.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created on 2017/8/4.
 *
 * @author Alan
 * @since 1.0
 */
@Component
public class LogicManager {

    @ResponseMessage(messageType = 1000, cmd = 2)
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
