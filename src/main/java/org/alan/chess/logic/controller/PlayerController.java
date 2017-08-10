package org.alan.chess.logic.controller;

import org.alan.chess.logic.bean.Player;
import org.alan.mars.protostuff.PFSession;

/**
 * Created on 2017/8/10.
 *
 * @author Alan
 * @since 1.0
 */
public class PlayerController {
    public PFSession session;
    public Player player;

    public void sendToClient(Object msg) {
    }
}
