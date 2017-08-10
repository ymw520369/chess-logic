/*
 * Copyright (c) 2017. Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 */

package org.alan.chess.logic.battle;

import org.alan.miner.config.GameConfig;
import org.alan.miner.data.role.Role;
import org.alan.miner.event.EventAnalyzer;
import org.alan.miner.event.EventMessage;
import org.alan.miner.event.EventTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 2017/3/22.
 *
 * @author Chow
 * @since 1.0
 */
@Service
public class BattleService {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private EventAnalyzer eventAnalyzer;

    @Autowired
    private GameConfig gameConfig;

    public void kill(Role role) {
        eventAnalyzer.tell(new EventMessage(EventTypeEnum.KILL, 1, 0, role.getRoleUid())); //每次只能杀1个人
    }

    public void mining(Role role) {
        eventAnalyzer.tell(new EventMessage(EventTypeEnum.MINING, 1, 0, role.getRoleUid()));
    }

    public void battleBegin(Role role) {
        role.setInBattle(true);
    }

    public boolean revive(Role role) {
        if (role.decreaseDiamond(gameConfig.getReviveNeedDiamond())) {
            return true;
        }
        return false;
    }
//
//    public void battleEnd(BattleEnd endDto) {
//        eventAnalyzer.tell(new EventMessage(EventTypeEnum.BATTLE_END, endDto, 0, endDto.getRoleUid()));
//    }
}
