/*
 * Copyright (c) 2017. Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 */

package org.alan.chess.logic.scene;

import org.alan.chess.logic.constant.GameResultEnum;
import org.alan.chess.logic.controller.PlayerController;
import org.alan.chess.logic.event.GameListener;
import org.alan.chess.logic.manager.PlayerListener;

import java.util.HashMap;
import java.util.Map;

/**
 * 场景控制器
 * <p>
 * Created on 2017/4/24.
 *
 * @author Alan
 * @since 1.0
 */
public abstract class SceneController<T>{
    public int uid;
    public T source;
    public long createTime;
    public boolean destroy;
    public Map<Long, PlayerController> playerMap;

    public SceneController(int uid, T source, long createTime, Map<Long, PlayerController> playerMap) {
        this.uid = uid;
        this.source = source;
        this.createTime = createTime;
        this.playerMap = playerMap;
    }

    public SceneController(int uid, T source) {
        this(uid, source, System.currentTimeMillis(), new HashMap<>());
    }

    public T getSource() {
        return source;
    }

    /**
     * 玩家进入场景方法
     *
     * @param playerController
     * @return
     */
    public GameResultEnum enter(PlayerController playerController) {
        playerMap.put(playerController.playerId(), playerController);
        playerController.setSceneId(uid);
        return GameResultEnum.SUCCESS;
    }

    /**
     * 玩家退出场景方法
     *
     * @param playerController
     * @return
     */
    public GameResultEnum exit(PlayerController playerController) {
        playerMap.remove(playerController.playerId());
        playerController.setSceneId(0);
        return GameResultEnum.SUCCESS;
    }

    /**
     * 场景注销方法
     *
     * @return
     */
    public void destroy() {
        playerMap.clear();
        destroy = true;
    }

    /**
     * 是否已经销毁
     *
     * @return
     */
    public boolean isDestroy() {
        return destroy;
    }

    /**
     * 向场景中所有人广播消息
     *
     * @param msg
     */
    public void broadcast(Object msg) {
        playerMap.values().forEach(p -> p.sendToClient(msg));
    }
}
