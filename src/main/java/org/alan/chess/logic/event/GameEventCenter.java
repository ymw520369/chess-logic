/**
 * Copyright Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 * <p>
 * 2016年6月1日
 */
package org.alan.chess.logic.event;

import org.alan.mars.timer.TimerCenter;
import org.alan.mars.timer.TimerEvent;
import org.alan.mars.timer.TimerListener;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 用户行为分析器，收集用户行为
 *
 * @author Alan
 * @since 11.0
 */
@Component
public class GameEventCenter implements ApplicationRunner, TimerListener {

    Logger log = Logger.getLogger(getClass());

    /**
     * 消息队列
     */
    private LinkedBlockingQueue<GameEvent> messages = new LinkedBlockingQueue<>();

    /**
     * 分消息类型监听器
     */
    private Map<Class<GameEvent>, List<GameListener>> gameEventListMap = new HashMap<>();

    @Autowired
    TimerCenter timerCenter;


    /**
     * 添加监听指定消息类型的消息，如果要监听所有消息，type传0
     *
     * @param gameListener
     * @param eventTypes
     */
    public void addBehaviorListener(GameListener gameListener, Class<GameEvent>... eventTypes) {
        if (eventTypes != null && eventTypes.length > 0) {
            for (Class<GameEvent> clazz : eventTypes) {
                List<GameListener> bs = gameEventListMap.get(clazz);
                if (bs == null) {
                    bs = new CopyOnWriteArrayList<>();
                    gameEventListMap.put(clazz, bs);
                }
                bs.add(gameListener);
            }
        }
    }

    public void removeListener(GameListener gameListener, Class<GameEvent>... eventTypes) {
        if (eventTypes != null && eventTypes.length > 0) {
            for (Class<GameEvent> clazz : eventTypes) {
                List<GameListener> bs = gameEventListMap.get(clazz);
                if (bs != null) {
                    bs.remove(gameListener);
                }
            }
        }
    }

    public void removeListener(GameListener gameListener) {
        gameEventListMap.values().forEach(list -> list.remove(gameListener));
    }

    public void notifyListener(GameEvent gameEvent) {
        // 找到监听该消息的监听器
        List<GameListener> listeners = gameEventListMap.get(gameEvent.getClass());
        if (listeners != null) {
            listeners.forEach(listener -> listener.happen(gameEvent));
        }
    }

    public void tell(GameEvent gameEvent) {
        messages.offer(gameEvent);
    }

    @Override
    public void onTimer(TimerEvent e) {
        GameEvent gameEvent = messages.poll();
        if (gameEvent != null) {
            notifyListener(gameEvent);
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        timerCenter.add(new TimerEvent<>(this, "BehaviorAnalyzer", 50));
    }
}
