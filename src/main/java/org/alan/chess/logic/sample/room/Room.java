/*
 * Copyright (c) 2017. Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 */

package org.alan.chess.logic.sample.room;

import org.alan.chess.logic.sample.scene.Scene;
import org.alan.mars.sample.SampleFactory;
import org.alan.mars.sample.impl.SampleFactoryImpl;

/**
 * Created on 2017/4/24.
 *
 * @author Alan
 * @since 1.0
 */
public class Room extends Scene {
    public static SampleFactory<Room> factory = new SampleFactoryImpl<>();

    private int battleSid;
    private int num = 4;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getBattleSid() {
        return battleSid;
    }
}
