/*
 * Copyright (c) 2017. Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 */

package org.alan.chess.logic.battle;

import org.alan.chess.logic.battle.BattleMessage.MoveChess;
import org.alan.chess.logic.battle.sprite.SpriteController;
import org.alan.chess.logic.battle.sprite.SpriteManager;
import org.alan.chess.logic.match.MatchInfo;
import org.alan.chess.logic.match.TeamInfo;
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
    public TeamInfo[] teamInfos;
    public Lock inputLock = new ReentrantLock();
    public TimerCenter timerCenter;
    public BattleListener battleListener;
    public SpriteController[][] pointSprites;

    private Logger log = LoggerFactory.getLogger(getClass());

    protected BattleController(int uid, Battle battle, Set<MatchInfo> matchInfos) {
        super(uid, battle);
        teamInfos = new TeamInfo[matchInfos.size()];
        int i = 0;
        //初始化队伍
        for (MatchInfo matchInfo : matchInfos) {
            TeamInfo teamInfo = matchInfo.getTeamInfo();
            teamInfo.teamId = i++;
            teamInfos[teamInfo.teamId] = teamInfo;
        }
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
            spriteController.battleController = this;
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
        PlayerFighter playerFighter = getFighter(roleUid);
        if (playerFighter != null) {
            playerFighter.initDone = true;
        }
        boolean allDone = Arrays.stream(teamInfos).allMatch(TeamInfo::battleInitDone);
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
        BattlePoint point = moveChess.fromPoint;
        SpriteController spriteController = pointSprites[point.x][point.z];
        PlayerFighter fighter = getFighter(roleUid);
        //如果当前是该玩家出手，并且要行动的游戏对象属于该玩家
        if (fighter.teamId == currentTeamId && currentTeamId == spriteController.sprite.team) {
            boolean result = spriteController.moveTo(moveChess.toPoint);
            //移动成功广播消息
            if (result) {
                BattleMessage.sendMove(this, roleUid, moveChess);
                nextRound();
            }
        }
    }

    public void update() {

    }

    public void nextRound() {

    }

    public PlayerFighter getFighter(long playerId) {
        for (TeamInfo teamInfo : teamInfos) {
            PlayerFighter playerFighter = teamInfo.fighters.get(playerId);
            if (playerFighter != null) {
                return playerFighter;
            }
        }
        return null;
    }

    public void destroy() {
        timerCenter.remove(this);
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
