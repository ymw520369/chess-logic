/*
 * Copyright (c) 2017. Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 */

package org.alan.chess.logic.battle;

import org.alan.chess.logic.battle.BattleMessage.MoveChess;
import org.alan.chess.logic.controller.PlayerController;
import org.alan.mars.protostuff.Command;
import org.alan.mars.protostuff.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created on 2017/3/20.
 *
 * @author Alan
 * @since 1.0
 */
@MessageType(1200)
@Component
public class BattleMessageHandler {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private BattleManager battleManager;

    @Command(1201)
    public void battleBegin(PlayerController playerController) {

    }

    @Command(1203)
    public void gameInitDone(PlayerController playerController) {
        BattleController battleController = battleManager.find(playerController.sceneId());
        long roleUid = playerController.playerId();
        if (battleController == null) {
            log.warn("找不到玩家的战斗信息,roleUid={}", roleUid);
        } else {
            battleController.initDone(roleUid);
        }
    }

    @Command(1205)
    public void move(PlayerController playerController, MoveChess moveChess) {
        BattleController battleController = battleManager.find(playerController.sceneId());
        long roleUid = playerController.playerId();
        if (battleController == null) {
            log.warn("找不到玩家的战斗信息,roleUid={}", roleUid);
        } else {
            battleController.move(roleUid, moveChess);
        }
    }

}
