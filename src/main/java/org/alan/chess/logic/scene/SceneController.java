/*
 * Copyright (c) 2017. Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 */

package org.alan.chess.logic.scene;

import org.alan.chess.logic.controller.PlayerController;
import org.alan.mars.protostuff.ResponseMessage;

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
public abstract class SceneController<T> {
    public int uid;
    public T source;
    public long createTime;
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
    public boolean enterScene(PlayerController playerController) {
        playerMap.put(playerController.playerId(), playerController);
        playerController.setSceneId(0);
        broadcast(new RespEnterScene(uid, playerController.playerId()));
        return true;
    }

    /**
     * 玩家退出场景方法
     *
     * @param playerController
     * @return
     */
    public boolean exitScene(PlayerController playerController) {
        playerMap.remove(playerController.playerId());
        playerController.setSceneId(uid);
        broadcast(new RespExitScene(uid, playerController.playerId()));
        return true;
    }

    /**
     * 向场景中所有人广播消息
     *
     * @param msg
     */
    public void broadcast(Object msg) {
        playerMap.values().forEach(p -> p.sendToClient(msg));
    }

    @ResponseMessage(messageType = 1003, cmd = 1)
    public static class RespEnterScene {
        public int sceneId;
        public long playerId;

        public RespEnterScene(int sceneId, long playerId) {
            this.sceneId = sceneId;
            this.playerId = playerId;
        }
    }

    @ResponseMessage(messageType = 1003, cmd = 2)
    public static class RespExitScene {
        public int sceneId;
        public long playerId;

        public RespExitScene(int sceneId, long playerId) {
            this.sceneId = sceneId;
            this.playerId = playerId;
        }
    }
}
