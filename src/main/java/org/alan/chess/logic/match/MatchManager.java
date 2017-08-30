/*
 * Copyright (c) 2017. Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 */

package org.alan.chess.logic.match;

import org.alan.chess.logic.battle.BattleManager;
import org.alan.chess.logic.room.RoomController;
import org.alan.chess.logic.sample.battle.Battle;
import org.alan.chess.logic.sample.scene.Room;
import org.alan.mars.timer.TimerCenter;
import org.alan.mars.timer.TimerEvent;
import org.alan.mars.timer.TimerListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created on 2017/4/24.
 *
 * @author Alan
 * @since 1.0
 */
@Component
public class MatchManager implements TimerListener, CommandLineRunner {
    private final static int MATCH_SIZE = 2;

    /* 匹配队列，以不同的房间类型保存不同的匹配队列*/
    private Map<Room, ConcurrentLinkedQueue<MatchInfo>> matchingMap = new ConcurrentHashMap<>();
    /* 以每个房间的唯一ID作为键，做一个hash映射*/
    private Map<Integer, MatchInfo> indexMap = new ConcurrentHashMap<>();
    @Autowired
    private BattleManager battleManager;

    @Autowired
    private TimerCenter timerCenter;

    @Override
    public void run(String... args) throws Exception {
        timerCenter.add(new TimerEvent<>(this, this, 20));
    }

    /**
     * 添加匹配房间
     *
     * @param roomController
     * @return
     */
    public MatchInfo addMatch(RoomController roomController) {
        Room room = roomController.getSource();
        int roomUid = roomController.uid;
        if (indexMap.containsKey(roomUid)) {
            MatchInfo matchInfo = indexMap.get(roomUid);
            return matchInfo;
        } else {
            MatchInfo matchInfo = new MatchInfo(System.currentTimeMillis(), roomController);
            ConcurrentLinkedQueue<MatchInfo> matchingQueue =
                    matchingMap.computeIfAbsent(room, e -> new ConcurrentLinkedQueue<>());
            matchingQueue.add(matchInfo);
            indexMap.put(roomUid, matchInfo);
            return matchInfo;
        }
    }

    /**
     * 取消匹配
     *
     * @param roomController
     * @return
     */
    public MatchInfo cancelMatch(RoomController roomController) {
        int roomUid = roomController.uid;
        MatchInfo matchInfo = indexMap.get(roomUid);
        if (matchInfo != null) {
            Room room = roomController.getSource();
            ConcurrentLinkedQueue<MatchInfo> matchingQueue = matchingMap.computeIfAbsent(room, e -> new ConcurrentLinkedQueue<>());
            matchingQueue.remove(matchInfo);
            indexMap.remove(roomUid);
        }
        return matchInfo;
    }

    @Override
    public void onTimer(TimerEvent timerEvent) {
        for (Map.Entry<Room, ConcurrentLinkedQueue<MatchInfo>> entry : matchingMap.entrySet()) {
            synchronized (entry.getKey()) {
                ConcurrentLinkedQueue<MatchInfo> matchingQueue = entry.getValue();
                if (matchingQueue == null || matchingQueue.isEmpty()) {
                    continue;
                }
                MatchInfo matchInfo = matchingQueue.poll();
                Set<MatchInfo> matchInfos = new HashSet<>();
                matchInfos.add(matchInfo);
                if (matchInfo != null && !matchingQueue.isEmpty()) {
                    Iterator<MatchInfo> iter = matchingQueue.iterator();
                    while (iter.hasNext()) {
                        MatchInfo PlayerMatchInfo1 = iter.next();
                        if (matchInfo.match(PlayerMatchInfo1)) {
                            iter.remove();
                            matchInfos.add(PlayerMatchInfo1);
                        }
                        if (matchInfos.size() == MATCH_SIZE) {
                            break;
                        }
                    }
                }
                if (matchInfos.size() < MATCH_SIZE) {
                    matchInfos.forEach(e -> matchingQueue.offer(e));
                } else {
                    matchSussessful(entry.getKey(), matchInfos);
                }
            }
        }
    }

    private void matchSussessful(Room room, Set<MatchInfo> matchInfos) {
        battleManager.startBattle(Battle.newBattle(room.battleSid), matchInfos);
    }
}
