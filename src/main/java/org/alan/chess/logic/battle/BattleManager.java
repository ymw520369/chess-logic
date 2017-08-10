/*
 * Copyright (c) 2017. Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 */

package org.alan.chess.logic.battle;

import org.alan.chess.logic.match.MatchInfo;
import org.alan.mars.timer.TimerCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created on 2017/4/25.
 *
 * @author Alan
 * @since 1.0
 */
@Component
public class BattleManager implements BattleListener {
    private Map<Integer, BattleController> battles = new HashMap<>();
    private AtomicInteger uidCreator = new AtomicInteger();
    private BattleMessageHelper battleMessageHelper;
    @Autowired
    private TimerCenter timerCenter;

    @Bean
    private BattleMessageHelper createHelper() {
        return battleMessageHelper = new BattleMessageHelper();
    }

    private BattleController create(Battle battle, Set<MatchInfo> matchInfos) {
        BattleController battleController = new BattleController(battleMessageHelper, uidCreator.incrementAndGet(),
                battle, matchInfos).timerCenter(timerCenter).battleListener(this);
        battles.put(battleController.getUid(), battleController);
        return battleController;
    }

    public void startBattle(Battle battle, Set<MatchInfo> matchInfos) {
        BattleController battleController = create(battle, matchInfos);
        battleController.init();
    }

    public BattleController find(int uid) {
        return battles.get(uid);
    }

    @Override
    public void destroy(BattleController battleController) {
        battles.remove(battleController.getUid());
    }
}
