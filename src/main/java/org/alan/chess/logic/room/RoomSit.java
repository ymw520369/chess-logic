package org.alan.chess.logic.room;

import org.alan.chess.logic.controller.PlayerController;

/**
 * Created on 2017/8/14.
 *
 * @author Alan
 * @since 1.0
 */
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
