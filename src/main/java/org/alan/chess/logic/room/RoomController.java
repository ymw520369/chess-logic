/*
 * Copyright (c) 2017. Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 */

package org.alan.chess.logic.room;

import org.alan.chess.logic.constant.GameResultEnum;
import org.alan.chess.logic.controller.PlayerController;
import org.alan.chess.logic.match.MatchInfo;
import org.alan.chess.logic.match.MatchManager;
import org.alan.chess.logic.sample.scene.Room;
import org.alan.chess.logic.scene.SceneController;

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
    //是否是快速匹配房间
    public boolean isQuickRoom;

    public MatchManager matchManager;

    RoomController(Room room, int uid, PlayerController playerController, MatchManager matchManager) {
        super(uid, room);
        roomSits = new RoomSit[room.maxNum];
        for (int i = 0; i < roomSits.length; i++) {
            roomSits[i] = new RoomSit(i);
        }
        ownerSit = 0;
        this.matchManager = matchManager;
        enter(playerController);
    }

    @Override
    public GameResultEnum enter(PlayerController playerController) {
        super.enter(playerController);
        RoomSit roomSit = sit(playerController);
        if (roomSit != null) {
            return GameResultEnum.SUCCESS;
        }
        return GameResultEnum.FAILURE;
    }

    @Override
    public GameResultEnum exit(PlayerController playerController) {
        super.exit(playerController);
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

    public GameResultEnum beginMatch(PlayerController playerController) {
        if (isOwner(playerController) && roomStatus == RoomStatus.WAIT) {
            MatchInfo matchInfo = matchManager.addMatch(this);
            roomStatus = RoomStatus.MATCH;
            broadcast(new RoomMessage.RespBeginMatch(matchInfo.beginTime));
            return GameResultEnum.SUCCESS;
        }
        return GameResultEnum.ILLEGAL;
    }

    public GameResultEnum cancelMatch(PlayerController playerController) {
        if (isOwner(playerController) && roomStatus == RoomStatus.MATCH) {
            matchManager.cancelMatch(this);
            roomStatus = RoomStatus.WAIT;
            broadcast(new RoomMessage.RespCancelMatch(GameResultEnum.SUCCESS));
            if (isQuickRoom) {
                exit(playerController);
                destroy();
            }
            return GameResultEnum.SUCCESS;
        }
        return GameResultEnum.ILLEGAL;
    }
}
