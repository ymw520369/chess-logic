/*
 * Copyright (c) 2017. Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 */

package org.alan.chess.logic.battle;

import org.alan.chess.logic.controller.PlayerController;

/**
 * Created on 2017/4/25.
 *
 * @author Alan
 * @since 1.0
 */
public class PlayerFighter {
    public long playerId;
    public String playerName;
    public transient PlayerController playerController;
    public transient boolean initDone;

    public PlayerFighter(PlayerController playerController) {
        this.playerController = playerController;
        this.playerId = playerController.playerId();
        this.playerName = playerController.playerName();
    }

    public void send(Object msg) {
        playerController.sendToClient(msg);
    }

    public PlayerController getPlayerController() {
        return playerController;
    }
}
