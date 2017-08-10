package org.alan.chess.logic.match;

import org.alan.chess.logic.room.RoomController;

/**
 * Created on 2017/8/10.
 *
 * @author Alan
 * @since 1.0
 */
public class MatchInfo {
    public long beginTime;
    public RoomController roomController;

    public MatchInfo(long beginTime, RoomController roomController) {
        this.beginTime = beginTime;
        this.roomController = roomController;
    }

    public boolean match(MatchInfo matchInfo) {
        //TODO
        return true;
    }
}
