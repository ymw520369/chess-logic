package org.alan.chess.logic.room;

import org.alan.chess.logic.constant.GameResultEnum;
import org.alan.chess.logic.constant.MessageConst;
import org.alan.mars.protostuff.ProtobufMessage;
import org.alan.mars.protostuff.RequestMessage;
import org.alan.mars.protostuff.ResponseMessage;
import org.omg.CORBA.PUBLIC_MEMBER;

/**
 * 房间消息帮助者
 * <p>
 * Created by Alan on 2017/8/11.
 */
public interface RoomMessage {
    /**
     * 创建房间消息
     */
    @ProtobufMessage
    class ReqCreateRoom {
        public int roomType;
    }

    /**
     * 创建房间消息
     */
    @ProtobufMessage(resp = true, messageType = MessageConst.Room.TYPE, cmd = MessageConst.Room.RESP_CREATE_ROOM)
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
    @ProtobufMessage(resp = true, messageType = MessageConst.Room.TYPE, cmd = MessageConst.Room.RESP_BEGIN_MATCH)
    class RespBeginMatch {
        public long beginTime;

        public RespBeginMatch(long beginTime) {
            this.beginTime = beginTime;
        }
    }

    /**
     * 返回开始匹配消息
     */
    @ProtobufMessage(resp = true, messageType = MessageConst.Room.TYPE, cmd = MessageConst.Room.RESP_CANEL_MATCH)
    class RespCancelMatch {
        public GameResultEnum gameResultEnum;

        public RespCancelMatch(GameResultEnum gameResultEnum) {
            this.gameResultEnum = gameResultEnum;
        }
    }


}
