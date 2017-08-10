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
@Component
public class SceneManager {
    private Map<Integer, SceneController> sceneMap = new HashMap<>();

    public SceneController find(int uid) {
        return sceneMap.get(uid);
    }
}
