/*
 * Copyright (c) 2017. Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 */

package org.alan.chess.logic.battle;

import org.alan.chess.logic.match.MatchInfo;
import org.alan.chess.logic.scene.SceneController;
import org.alan.mars.timer.TimerCenter;
import org.alan.mars.timer.TimerEvent;
import org.alan.mars.timer.TimerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created on 2017/4/25.
 *
 * @author Alan
 * @since 1.0
 */
public class BattleController extends SceneController<Battle> implements TimerListener {
    private int lfs = 1;
    private int seed;
    private List<PlayerFighter> fighters;
    private BattleMessageHelper battleMessageHelper;
    private List<GameInput> inputs = new LinkedList<>();
    private Lock inputLock = new ReentrantLock();
    private TimerCenter timerCenter;
    private BattleListener battleListener;

    private Logger log = LoggerFactory.getLogger(getClass());

    protected BattleController(BattleMessageHelper battleMessageHelper, int uid, Battle battle, Set<MatchInfo> matchInfos) {
        super(uid, battle, System.currentTimeMillis());
        fighters = new ArrayList<>();
        this.battleMessageHelper = battleMessageHelper;
        matchInfos.forEach(e -> fighters.addAll(e.roomController.getFighters()));
    }

    public BattleController timerCenter(TimerCenter timerCenter) {
        this.timerCenter = timerCenter;
        return this;
    }

    public BattleController battleListener(BattleListener battleListener) {
        this.battleListener = battleListener;
        return this;
    }

    public List<PlayerFighter> getFighters() {
        return fighters;
    }

    public void init() {
        seed = (int) (Math.random() * Integer.MAX_VALUE);
        fighters.forEach(e -> e.getPlayerController().player.sceneId = uid);
        battleMessageHelper.sendGameInit(this, seed);

    }

    public void initDone(long roleUid) {
        boolean allDone = true;
        for (PlayerFighter e : fighters) {
            if (e.getUid() == roleUid) {
                e.setInitDone(true);
            }
            allDone = allDone && e.isInitDone();
        }

        if (allDone) {
            start();
        }
    }

    public void start() {
        battleMessageHelper.sendGameStart(this);
        timerCenter.add(new TimerEvent(this, "FIXED_UPDATE", 100));
        timerCenter.add(new TimerEvent(this, "END", 0, 1, 3).withTimeUnit(TimeUnit.MINUTES));
    }

    public void input(long roleUid, GameInput gameInput) {
        try {
            inputLock.lock();
            //inputs.add(GameInput.newBuilder(gameInput).setRoleUid(roleUid).build());
        } finally {
            inputLock.unlock();
        }
    }

    public void update() {
        try {
            inputLock.lock();
            battleMessageHelper.broadcastGameInput(this, lfs++, inputs);
            inputs.clear();
        } finally {
            inputLock.unlock();
        }
    }

    public void destroy() {
        fighters.forEach(e -> e.getPlayerController().player.sceneId = 0);
        timerCenter.remove(this);
        fighters.clear();
        inputs.clear();
        battleListener.destroy(this);
    }

    public void broadcast(Object msg) {
        fighters.forEach(e -> e.send(msg));
    }

    @Override
    public void onTimer(TimerEvent e) {
        if (e.getParameter().equals("END")) {
            log.debug("战斗结束，uid=" + uid);
            destroy();
        } else {
            update();
        }
    }
}
