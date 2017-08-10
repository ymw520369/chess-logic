package org.alan.chess.logic.room;

import org.alan.mars.protostuff.RequestMessage;
import org.alan.mars.protostuff.ResponseMessage;

/**
 * 房间消息帮助者
 * <p>
 * Created by Alan on 2017/8/11.
 */
public interface RoomMessage {
    /**
     * 创建房间消息
     */
    @RequestMessage
    class ReqCreateRoom {
        public int roomType;
    }

    /**
     * 创建房间消息
     */
    @ResponseMessage(messageType = 1100, cmd = 1102)
    class RespCreateRoom {
        public int roomType;
        public int roomId;

        public RespCreateRoom(int roomType, int roomId) {
            this.roomType = roomType;
            this.roomId = roomId;
        }
    }

    /**
     * 返回开始匹配消息
     */
    @ResponseMessage(messageType = 1100, cmd = 1104)
    class RespBeginMatch {
        public long beginTime;

        public RespBeginMatch(long beginTime) {
            this.beginTime = beginTime;
        }
    }

    /**
     * 返回开始匹配消息
     */
    @ResponseMessage(messageType = 1100, cmd = 1106)
    class RespCancelMatch {
    }


}
