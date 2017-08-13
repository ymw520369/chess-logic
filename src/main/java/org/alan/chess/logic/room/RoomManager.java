/*
 * Copyright (c) 2017. Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 */

package org.alan.chess.logic.room;

import org.alan.chess.logic.controller.PlayerController;
import org.alan.chess.logic.manager.PlayerExitListener;
import org.alan.chess.logic.sample.room.Room;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created on 2017/4/25.
 *
 * @author Alan
 * @since 1.0
 */
@Component
public class RoomManager implements PlayerExitListener {
    private Map<Integer, RoomController> rooms = new HashMap<>();

    private AtomicInteger uidCreator = new AtomicInteger();
    private Room room = new Room();

    public RoomController create(int roomType, PlayerController playerController) {
//        Room room = Room.factory.getSample(sid);
        RoomController roomController = new RoomController(room, uidCreator.incrementAndGet(), playerController);
        rooms.put(roomController.uid, roomController);
        return roomController;
    }

    public RoomController find(int uid) {
        return rooms.get(uid);
    }

    @Override
    public void playerExit(PlayerController playerController) {
        //RoomController roomController = find(player.role.);
    }
}
