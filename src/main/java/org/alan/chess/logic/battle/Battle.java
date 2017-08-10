/*
 * Copyright (c) 2017. Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 */

package org.alan.chess.logic.battle;

import org.alan.mars.sample.Sample;
import org.alan.mars.sample.SampleFactory;
import org.alan.mars.sample.impl.SampleFactoryImpl;

/**
 * Created on 2017/4/25.
 *
 * @author Alan
 * @since 1.0
 */
public class Battle extends Sample {
    public static SampleFactory<Battle> factory = new SampleFactoryImpl<>();

    private int mapSid;

    public int getMapSid() {
        return mapSid;
    }

    public void setMapSid(int mapSid) {
        this.mapSid = mapSid;
    }
}
