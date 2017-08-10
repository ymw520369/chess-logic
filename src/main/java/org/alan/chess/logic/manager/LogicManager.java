package org.alan.chess.logic.manager;

import org.alan.chess.logic.bean.Player;
import org.alan.chess.logic.bean.Role;
import org.alan.chess.logic.bean.UserInfo;
import org.alan.mars.protostuff.PFSession;
import org.alan.mars.protostuff.ResponseMessage;

/**
 * Created on 2017/8/4.
 *
 * @author Alan
 * @since 1.0
 */
public class LogicManager {
    @ResponseMessage(messageType = 1000, cmd = 2)
    public static class EnterGame {
        public Role role;

        public EnterGame(Role role) {
            this.role = role;
        }
    }

    public void EnterGame(PFSession session, Role role, UserInfo userInfo) {
        Player player = new Player<PFSession>();
        player.role = role;
        player.userInfo = userInfo;
        player.session = session;
        session.setReference(player);
        session.send(new EnterGame(role));
    }
}
