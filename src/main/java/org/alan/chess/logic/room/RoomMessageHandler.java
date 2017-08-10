/*
 * Copyright (c) 2017. Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 */

package org.alan.chess.logic.room;

import org.alan.chess.logic.constant.GameResultEnum;
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

/**
 * Created on 2017/4/24.
 *
 * @author Alan
 * @since 1.0
 */
@Component
@MessageType(1100)
public class RoomMessageHandler {
    @Autowired
    private SceneManager sceneManager;
    @Autowired
    private MatchManager matchManager;
    @Autowired
    private RoomManager roomManager;

    private Logger log = LoggerFactory.getLogger(getClass());

    @Command(1101)
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

    @Command(1102)
    public void beginMatch(PlayerController playerController) {
        log.info("收到玩家匹配请求，roleUid={},session={}", playerController.player.role.roleUid);
        int sceneId = playerController.player.sceneId;
        SceneController sceneController = sceneManager.find(sceneId);
        if (sceneController instanceof RoomController) {
            RoomController roomController = (RoomController) sceneController;
            if (roomController.isOwner(playerController) && roomController.roomStatus == RoomStatus.WAIT) {
                MatchInfo matchInfo = matchManager.addMatch(roomController);
                roomController.broadcast(new RespBeginMatch(matchInfo.beginTime));
                return;
            }
        }
        MessageToClient.sendTimerGameTips(playerController.session, GameResultEnum.ILLEGAL);

    }

    @Command(1103)
    public void cancelMatch(PlayerController playerController) {
        log.debug("收到玩家取消匹配请求，roleUid={}", playerController.player.role.roleUid);
        int sceneId = playerController.player.sceneId;
        SceneController sceneController = sceneManager.find(sceneId);
        if (sceneController instanceof RoomController) {
            RoomController roomController = (RoomController) sceneController;
            if (roomController.isOwner(playerController) && roomController.roomStatus == RoomStatus.MATCH) {
                matchManager.cancelMatch(roomController);
                roomController.broadcast(new RespCancelMatch());
                return;
            }
        }
        MessageToClient.sendTimerGameTips(playerController.session, GameResultEnum.ILLEGAL);
    }


}
