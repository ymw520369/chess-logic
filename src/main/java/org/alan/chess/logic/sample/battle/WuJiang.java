
package org.alan.chess.logic.sample.battle;

import org.alan.chess.logic.core.Sample;
import org.alan.chess.logic.core.SampleFactory;
import org.alan.chess.logic.core.SampleFactoryImpl;
import com.dyuproject.protostuff.Tag;

import java.util.*;

/**
 * Auto generate by "Python tools"
 *
 * @Date 2017-08-11 18:37:23
 */
public class WuJiang extends Sample {
    public static SampleFactory<WuJiang> factory = new SampleFactoryImpl<>();

    public static WuJiang getWuJiang(int sid) {
        return factory.getSample(sid);
    }

    public static WuJiang newWuJiang(int sid) {
        return factory.newSample(sid);
    }

    @Tag(3)
    // 武将描述
    public String des;
    @Tag(4)
    // 可使用队伍
    public List<Integer> team;
    @Tag(5)
    // 可使用位置
    public List<Integer> type;
    @Tag(6)
    // 初始等级
    public int level;

}
