/**
 * Copyright Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 * <p>
 * 2016年6月6日
 */
package org.alan.chess.logic.event;


/**
 * @author Alan
 * @version 1.0
 */
public interface GameListener {

    /**
     * 发生了什么
     *
     * @param msg
     */
    void happen(GameEvent msg);
}
