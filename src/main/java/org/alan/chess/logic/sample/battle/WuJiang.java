
 package org.alan.chess.logic.sample.battle;

 import org.alan.mars.sample.Sample;
 import org.alan.mars.sample.SampleFactory;
 import org.alan.mars.sample.impl.SampleFactoryImpl;
 import com.dyuproject.protostuff.Tag;
 import org.alan.mars.protostuff.ProtobufMessage;
 import java.util.*;

/**
 * Auto generate by "Python tools"
 * 
 * @Date 2017-08-29 23:13:11
 */
 @ProtobufMessage
 public class WuJiang extends Sample{
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
