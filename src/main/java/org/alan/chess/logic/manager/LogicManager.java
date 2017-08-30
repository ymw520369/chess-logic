package org.alan.chess.logic.manager;

import org.alan.chess.logic.constant.MessageConst;
import org.alan.chess.logic.data.Player;
import org.alan.chess.logic.controller.PlayerController;
import org.alan.chess.logic.event.GameEventCenter;
import org.alan.chess.logic.event.PlayerOfflineEvent;
import org.alan.chess.logic.event.PlayerOnlineEvent;
import org.alan.mars.net.Session;
import org.alan.mars.net.SessionListener;
import org.alan.mars.protostuff.ProtobufMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.alan.chess.logic.constant.MessageConst.*;

/**
 * Created on 2017/8/4.
 *
 * @author Alan
 * @since 1.0
 */
@Component
public class LogicManager implements SessionListener {

    @ProtobufMessage(resp = true, messageType = Login.TYPE, cmd = Login.RESP_ENTER_GAME)
    public static class EnterGame {

        public Player player;

        public EnterGame(Player player) {
            this.player = player;
        }

    }

    @Autowired
    GameEventCenter gameEventCenter;

    public void enterGame(PlayerController playerController) {
        playerController.sendToClient(new EnterGame(playerController.player));
        playerController.session.setSessionListener(this);
        gameEventCenter.tell(new PlayerOnlineEvent(playerController.playerId()));
    }

    @Override
    public void onSessionClose(Session session) {
        PlayerController playerController = (PlayerController) session.getReference();
        if (playerController != null) {
            gameEventCenter.tell(new PlayerOfflineEvent(playerController.playerId()));
        }
    }
}
