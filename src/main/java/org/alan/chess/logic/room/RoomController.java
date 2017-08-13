/*
 * Copyright (c) 2017. Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 */

package org.alan.chess.logic.room;

import org.alan.chess.logic.battle.PlayerFighter;
import org.alan.chess.logic.constant.GameResultEnum;
import org.alan.chess.logic.controller.PlayerController;
import org.alan.chess.logic.match.MatchInfo;
import org.alan.chess.logic.sample.room.Room;
import org.alan.chess.logic.scene.SceneController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 2017/4/24.
 *
 * @author Alan
 * @since 1.0
 */
public class RoomController extends SceneController<Room> {
    public int ownerSit;
    public RoomSit[] roomSits;
    public int roomStatus;

    RoomController(Room room, int uid, PlayerController playerController) {
        super(uid, room);
        roomSits = new RoomSit[room.getNum()];
        roomSits[0] = new RoomSit(0, playerController);
        for (int i = 1; i < roomSits.length; i++) {
            roomSits[i] = new RoomSit(i);
        }
        ownerSit = 0;
    }

    public Map<Long, PlayerFighter> getFighters() {
        Map<Long, PlayerFighter> fighters = new HashMap<>();
        for (RoomSit sit : roomSits) {
            if (sit.playerController != null) {
                fighters.put(sit.playerController.playerId(), new PlayerFighter(sit.playerController));
            }
        }
        return fighters;
    }

    public RoomSit joinRoom(PlayerController playerController) {
        return sit(playerController);
    }

    public GameResultEnum quitRoom(PlayerController playerController) {
        RoomSit roomSit = findSit(playerController);
        if (roomSit != null) {
            roomSit.playerController = null;
            RoomSit firstSit = findFirstSit();
            if (firstSit != null) {
                if (roomSit.sit == ownerSit) {
                    ownerSit = firstSit.sit;
                }
            } else {
                destroy();
            }
            return GameResultEnum.SUCCESS;
        }
        return GameResultEnum.ILLEGAL;
    }

    public boolean isOwner(PlayerController playerController) {
        return roomSits[ownerSit].playerController == playerController;
    }

    public RoomSit[] getRoomSits() {
        return roomSits;
    }

    public void broadcast(Object msg) {
        for (RoomSit roomSit : roomSits) {
            if (roomSit.playerController != null) {
                roomSit.playerController.sendToClient(msg);
            }
        }
    }

    private boolean destroy() {

        return true;
    }

    private RoomSit findFirstSit() {
        for (RoomSit roomSit : roomSits) {
            if (roomSit.open && roomSit.playerController != null) {
                return roomSit;
            }
        }
        return null;
    }

    private RoomSit findSit(PlayerController playerController) {
        for (RoomSit roomSit : roomSits) {
            if (roomSit.playerController == playerController) {
                return roomSit;
            }
        }
        return null;
    }

    private RoomSit sit(PlayerController playerController) {
        for (int i = 0; i < roomSits.length; i++) {
            if (roomSits[i].open && roomSits[i].playerController == null) {
                roomSits[i].playerController = playerController;
                return roomSits[i];
            }
        }
        return null;
    }

    public class RoomSit {
        public int sit;
        public boolean open = true;
        public PlayerController playerController;

        public RoomSit(int sit) {
            this.sit = sit;
        }

        public RoomSit(int sit, PlayerController playerController) {
            this(sit);
            this.playerController = playerController;
        }
    }
}
