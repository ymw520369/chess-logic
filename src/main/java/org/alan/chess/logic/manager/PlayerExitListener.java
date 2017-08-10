/*
 * Copyright (c) 2017. Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 */

package org.alan.chess.logic.manager;

import org.alan.chess.logic.controller.PlayerController;

/**
 * Created on 2017/3/8.
 *
 * @author Alan
 * @since 1.0
 */
public interface PlayerExitListener {

    void playerExit(PlayerController playerController);

}
