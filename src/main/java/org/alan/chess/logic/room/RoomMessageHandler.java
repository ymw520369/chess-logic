/*
 * Copyright (c) 2017. Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 */

package org.alan.chess.logic.room;

import org.alan.chess.logic.constant.GameResultEnum;
import org.alan.chess.logic.constant.MessageConst;
import org.alan.chess.logic.controller.PlayerController;
import org.alan.chess.logic.match.MatchInfo;
import org.alan.chess.logic.match.MatchManager;
import org.alan.chess.logic.message.MessageToClient;
import org.alan.chess.logic.room.RoomMessage.ReqCreateRoom;
import org.alan.chess.logic.room.RoomMessage.RespBeginMatch;
import org.alan.chess.logic.room.RoomMessage.RespCancelMatch;
import org.alan.chess.logic.room.RoomMessage.RespCreateRoom;
import org.alan.chess.logic.scene.SceneController;
import org.alan.chess.logic.scene.SceneManager;
import org.alan.mars.protostuff.Command;
import org.alan.mars.protostuff.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.plugin2.message.Message;

/**
 * Created on 2017/4/24.
 *
 * @author Alan
 * @since 1.0
 */
@Component
@MessageType(MessageConst.Room.TYPE)
public class RoomMessageHandler {
    @Autowired
    private MatchManager matchManager;
    @Autowired
    private RoomManager roomManager;

    private Logger log = LoggerFactory.getLogger(getClass());

    @Command(MessageConst.Room.REQ_CREATE_ROOM)
    public void createRoom(PlayerController playerController, ReqCreateRoom reqCreateRoom) {
        int roomType = reqCreateRoom.roomType;
        log.debug("收到玩家创建房间请求，roleUid={},roomType={}", playerController.player.role.roleUid, roomType);
        RoomController roomController = roomManager.create(roomType, playerController);
        if (roomController != null) {
            RespCreateRoom respCreateRoom = new RespCreateRoom(roomType, roomController.uid);
            roomController.broadcast(respCreateRoom);
            return;
        }
        MessageToClient.sendTimerGameTips(playerController.session, GameResultEnum.ILLEGAL);
    }

    @Command(MessageConst.Room.REQ_BEGIN_MATCH)
    public void beginMatch(PlayerController playerController) {
        log.info("收到玩家匹配请求，roleUid={},session={}", playerController.player.role.roleUid);
        int sceneId = playerController.player.sceneId;
        SceneController sceneController = SceneManager.find(sceneId);
        GameResultEnum gameResultEnum = GameResultEnum.ILLEGAL;
        if (sceneController instanceof RoomController) {
            RoomController roomController = (RoomController) sceneController;
            gameResultEnum = roomController.beginMatch(playerController);
        }
        if (gameResultEnum != GameResultEnum.SUCCESS) {
            MessageToClient.sendTimerGameTips(playerController.session, gameResultEnum);
        }
    }

    @Command(MessageConst.Room.REQ_CANEL_MATCH)
    public void cancelMatch(PlayerController playerController) {
        log.debug("收到玩家取消匹配请求，roleUid={}", playerController.player.role.roleUid);
        int sceneId = playerController.player.sceneId;
        SceneController sceneController = SceneManager.find(sceneId);
        GameResultEnum gameResultEnum = GameResultEnum.ILLEGAL;
        if (sceneController instanceof RoomController) {
            RoomController roomController = (RoomController) sceneController;
            gameResultEnum = roomController.cancelMatch(playerController);
        }
        if (gameResultEnum != GameResultEnum.SUCCESS) {
            MessageToClient.sendTimerGameTips(playerController.session, gameResultEnum);
        }
    }

    @Command(MessageConst.Room.REQ_QUICK_MATCH)
    public void quickMatch(PlayerController playerController, ReqCreateRoom reqCreateRoom) {
        log.info("收到玩家快速匹配请求，roleUid={},session={}", playerController.player.role.roleUid);
        int sceneId = playerController.player.sceneId;
        SceneController sceneController = SceneManager.find(sceneId);
        GameResultEnum gameResultEnum = GameResultEnum.ILLEGAL;
        if (sceneController == null || !(sceneController instanceof RoomController)) {
            int roomType = reqCreateRoom.roomType;
            RoomController roomController = roomManager.create(roomType, playerController,true);
            gameResultEnum = roomController.beginMatch(playerController);
        }
        if (gameResultEnum != GameResultEnum.SUCCESS) {
            MessageToClient.sendTimerGameTips(playerController.session, gameResultEnum);
        }
    }

}
