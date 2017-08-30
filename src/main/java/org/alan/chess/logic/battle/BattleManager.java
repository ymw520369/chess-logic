/*
 * Copyright (c) 2017. Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 */

package org.alan.chess.logic.battle;

import org.alan.chess.logic.match.MatchInfo;
import org.alan.chess.logic.sample.battle.Battle;
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
    @Autowired
    private TimerCenter timerCenter;

    public int getUid() {
        return uidCreator.incrementAndGet();
    }

    private BattleController create(Battle battle, Set<MatchInfo> matchInfos) {
        BattleController battleController = new BattleController(getUid(),
                battle, matchInfos).timerCenter(timerCenter).battleListener(this);
        battles.put(battleController.uid, battleController);
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
        battles.remove(battleController.uid);
    }
}
