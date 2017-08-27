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

/**
 * Created on 2017/4/25.
 *
 * @author Alan
 * @since 1.0
 */
public class BattleController extends SceneController<Battle> implements TimerListener {
    public final static int UNINIT_TEAMID = -1;
    public final static int TEAM_HOME = 1, TEAM_HEI = 2;

    //////////////////////////下面为实例变量//////////////////////////
    /* 定时器*/
    TimerCenter timerCenter;
    /* 战斗监听器*/
    BattleListener battleListener;

    /* 当前出手玩家*/
    public long currentPlayerId;
    /* 当前出手队伍*/
    public int currentTeamId = UNINIT_TEAMID;
    /* 当前回合数*/
    public int roundNum = 1;
    /* 本局游戏队伍信息*/
    public TeamInfo[] teamInfos;
    /* 游戏对象*/
    public SpriteController[][] pointSprites;
    /* 玩家出手倒计时*/
    private TimerEvent countdownEvent;

    private Logger log = LoggerFactory.getLogger(getClass());

    protected BattleController(int uid, Battle battle, Set<MatchInfo> matchInfos) {
        super(uid, battle);
        int len = matchInfos.size();
        teamInfos = new TeamInfo[len];
        int i = (int) (Math.random() * len);
        //初始化队伍
        for (MatchInfo matchInfo : matchInfos) {
            TeamInfo teamInfo = matchInfo.getTeamInfo();
            teamInfo.teamId = (++i) % len;
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
        nextTeam();
    }

    public void stop() {
        destroy();
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
                nextTeam();
            }
        }
    }

    public void nextTeam() {
        if (countdownEvent != null) {
            countdownEvent.setEnabled(false);
        }
        boolean newRound = false;
        //如果当前出手队伍是最后一个队伍或者还未进行初始化，则开始新的一回合
        if (currentTeamId == UNINIT_TEAMID || currentTeamId == teamInfos.length - 1) {
            nextRound();
            newRound = true;
        } else {
            currentTeamId++;
        }
        currentPlayerId = teamInfos[currentTeamId].fighter().playerId;
        BattleMessage.sendCurrentGoInfo(this, newRound);
        //服务器开始计时
        countdownEvent = new TimerEvent<>(this, source.countdownSecond, "COUNTDOWN")
                .withTimeUnit(TimeUnit.SECONDS);
        timerCenter.add(countdownEvent);
    }

    public void nextRound() {
        roundNum++;
        //如果回合数达到最大回合数，强制结束本局战斗
        if (roundNum >= source.maxRoundNum) {
            stop();
            return;
        }
        currentTeamId = 0;
    }

    public PlayerFighter getFighter(long playerId) {
        for (TeamInfo teamInfo : teamInfos) {
            PlayerFighter playerFighter = teamInfo.fighter();
            if (playerFighter.playerId == playerId) {
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
        if (e == countdownEvent) {
            nextTeam();
        }
    }
}
