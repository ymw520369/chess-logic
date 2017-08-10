/*
 * Copyright (c) 2017. Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 */

package org.alan.chess.logic.scene;

/**
 * 场景控制器
 * <p>
 * Created on 2017/4/24.
 *
 * @author Alan
 * @since 1.0
 */
public abstract class SceneController<T> {
    public int uid;
    public T source;
    public long createTime;

    public SceneController(int uid, T source, long createTime) {
        this.uid = uid;
        this.source = source;
    }

    public T getSource() {
        return source;
    }
}
