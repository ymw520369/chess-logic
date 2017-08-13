/*
 * Copyright (c) 2017. Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 */

package org.alan.chess.logic.battle;

import org.alan.chess.logic.battle.BattleMessage.MoveChess;
import org.alan.chess.logic.battle.sprite.SpriteController;
import org.alan.chess.logic.battle.sprite.SpriteManager;
import org.alan.chess.logic.match.MatchInfo;
import org.alan.chess.logic.sample.battle.Battle;
import org.alan.chess.logic.sample.battle.CardSprite;
import org.alan.chess.logic.scene.SceneController;
import org.alan.mars.timer.TimerCenter;
import org.alan.mars.timer.TimerEvent;
import org.alan.mars.timer.TimerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
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
    public int currentPlayerId;
    public int currentTeamId;
    public int roundNum;
    public Map<Long, PlayerFighter> fighters;
    public Map<Integer, PlayerFighter> teamFighters;
    public Lock inputLock = new ReentrantLock();
    public TimerCenter timerCenter;
    public BattleListener battleListener;
    public SpriteController[][] pointSprites;

    private Logger log = LoggerFactory.getLogger(getClass());

    protected BattleController(int uid, Battle battle, Set<MatchInfo> matchInfos) {
        super(uid, battle);
        fighters = new HashMap<>();
        matchInfos.forEach(e -> fighters.putAll(e.roomController.getFighters()));
    }

    public BattleController timerCenter(TimerCenter timerCenter) {
        this.timerCenter = timerCenter;
        return this;
    }

    public BattleController battleListener(BattleListener battleListener) {
        this.battleListener = battleListener;
        return this;
    }

    public void initSprite() {
        pointSprites = new SpriteController[source.row][source.cell];
        Collection<CardSprite> sprites = CardSprite.factory.getAllSamples();
        sprites.forEach(sprite -> {
            SpriteController spriteController = SpriteManager.create(sprite);
            pointSprites[sprite.x][sprite.z] = spriteController;
        });
    }

    public void init() {
        //初始化战斗元素
        initSprite();
        //发送初始化消息
        BattleMessage.sendGameInit(this);
    }

    public void initDone(long roleUid) {
        PlayerFighter playerFighter = fighters.get(roleUid);
        if (playerFighter != null) {
            playerFighter.initDone = true;
        }
        boolean allDone = fighters.values().stream().allMatch(f -> f.initDone);
        if (allDone) {
            start();
        }
    }

    public void start() {
        BattleMessage.sendCurrentGoInfo(this, true);
        timerCenter.add(new TimerEvent<>(this, "FIXED_UPDATE", 100));
        timerCenter.add(new TimerEvent<>(this, "END", 0, 1, 3).withTimeUnit(TimeUnit.MINUTES));
    }


    public void move(long roleUid, MoveChess moveChess) {

    }

    public void update() {

    }

    public void nextRound() {

    }

    public void destroy() {
        timerCenter.remove(this);
        fighters.clear();
        battleListener.destroy(this);
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
