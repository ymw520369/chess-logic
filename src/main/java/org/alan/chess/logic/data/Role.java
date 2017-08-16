package org.alan.chess.logic.data;

import org.alan.mars.protostuff.ProtobufMessage;

/**
 * author Alan
 * eamil mingweiyang@foxmail.com
 * date 2017/8/16
 */
@ProtobufMessage
public class Role {
    public long userId;
    public long roleUid;
    public String name;
    public int zoneId;
}
