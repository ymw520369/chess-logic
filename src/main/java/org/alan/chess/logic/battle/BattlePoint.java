package org.alan.chess.logic.battle;

import org.alan.mars.protostuff.ProtobufMessage;

/**
 * 战斗坐标数据结构
 * <p>
 * Created by Alan on 2017/8/12.
 */
@ProtobufMessage
public class BattlePoint {
    public int x;
    public int z;
}
