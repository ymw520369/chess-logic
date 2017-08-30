/*
 * Copyright (c) 2017. Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 */

package org.alan.chess.logic.scene;

import org.alan.mars.timer.TimerCenter;
import org.alan.mars.timer.TimerEvent;
import org.alan.mars.timer.TimerListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 场景管理器，用于初始化
 * <p>
 * Created on 2017/4/24.
 *
 * @author Alan
 * @since 1.0
 */
@Component
public class SceneManager implements ApplicationRunner, TimerListener {

    private static Map<Integer, SceneController> sceneMap = new ConcurrentHashMap<>();

    public static SceneController find(int uid) {
        SceneController sc = sceneMap.get(uid);
        return sc == null || sc.isDestroy() ? null : sc;
    }

    protected static void addSceneController(SceneController sceneController) {
        sceneMap.put(sceneController.uid, sceneController);
    }

    @Autowired
    TimerCenter timerCenter;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        timerCenter.add(new TimerEvent<>(this, "SCENE-MANAGER", 30).withTimeUnit(TimeUnit.SECONDS));
    }

    @Override
    public void onTimer(TimerEvent e) {
        sceneMap.entrySet().removeIf(entry -> entry.getValue().isDestroy());
    }
}
