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
    private PlayerController playerController;
    private boolean initDone;

    public PlayerFighter(PlayerController playerController) {
        this.playerController = playerController;
    }

    public long getUid() {
        return playerController.player.role.roleUid;
    }

    public String getName() {
        return playerController.player.role.name;
    }

    public boolean isInitDone() {
        return initDone;
    }

    public void setInitDone(boolean initDone) {
        this.initDone = initDone;
    }

    public void send(Object msg) {
        playerController.sendToClient(msg);
    }

    public PlayerController getPlayerController() {
        return playerController;
    }
}
