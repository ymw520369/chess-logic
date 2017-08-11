package org.alan.chess.logic.core;

import com.dyuproject.protostuff.Tag;

/**
 * Created on 2017/7/10.
 *
 * @author Alan
 * @since 1.0
 */
public abstract class Sample {
    /**
     * ����id
     */
    @Tag(1)
    public int sid;

    /**
     * ��������
     */
    @Tag(2)
    public String name;

    @Override
    public Sample clone() {
        try {
            return (Sample) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(
                    getClass().getName() + " clone, sid=" + sid, e);
        }
    }
}
