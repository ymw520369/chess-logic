/*
 * Copyright (c) 2017. Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 */

package org.alan.chess.logic.scene;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 场景管理器，用于初始化
 * <p>
 * Created on 2017/4/24.
 *
 * @author Alan
 * @since 1.0
 */
public class SceneManager {
    private static Map<Integer, SceneController> sceneMap = new HashMap<>();

    public static SceneController find(int uid) {
        return sceneMap.get(uid);
    }

    protected static void addSceneController(SceneController sceneController) {
        sceneMap.put(sceneController.uid, sceneController);
    }
}
