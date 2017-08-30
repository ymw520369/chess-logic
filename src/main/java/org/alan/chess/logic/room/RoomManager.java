/*
 * Copyright (c) 2017. Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 */

package org.alan.chess.logic.room;

import org.alan.chess.logic.controller.PlayerController;
import org.alan.chess.logic.manager.PlayerListener;
import org.alan.chess.logic.match.MatchManager;
import org.alan.chess.logic.sample.scene.Room;
import org.alan.chess.logic.scene.SceneManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created on 2017/4/25.
 *
 * @author Alan
 * @since 1.0
 */
@Component
public class RoomManager extends SceneManager implements PlayerListener {

    private AtomicInteger uidCreator = new AtomicInteger();

    @Autowired
    private MatchManager matchManager;

    public int getUid() {
        return uidCreator.incrementAndGet();
    }

    public RoomController create(int roomSid, PlayerController playerController) {
        Room room = Room.factory.getSample(roomSid);
        RoomController roomController = new RoomController(room, getUid(), playerController, matchManager);
        addSceneController(roomController);
        return roomController;
    }

    public RoomController create(int roomSid, PlayerController playerController, boolean isQuickMatch) {
        Room room = Room.factory.getSample(roomSid);
        RoomController roomController = new RoomController(room, getUid(), playerController, matchManager);
        roomController.isQuickRoom = isQuickMatch;
        addSceneController(roomController);
        return roomController;
    }

    @Override
    public void playerExit(PlayerController playerController) {
        //RoomController roomController = find(player.role.);
    }
}
