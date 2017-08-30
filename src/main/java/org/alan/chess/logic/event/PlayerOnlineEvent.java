package org.alan.chess.logic.event;

/**
 * author Alan
 * eamil mingweiyang@foxmail.com
 * date 2017/8/31
 */
public class PlayerOnlineEvent extends GameEvent {
    public long playerId;

    public PlayerOnlineEvent(long playerId) {
        this.playerId = playerId;
    }
}
