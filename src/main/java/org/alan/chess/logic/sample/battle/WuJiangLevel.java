
 package org.alan.chess.logic.sample.battle;

 import org.alan.mars.sample.Sample;
 import org.alan.mars.sample.SampleFactory;
 import org.alan.mars.sample.impl.SampleFactoryImpl;
 import com.dyuproject.protostuff.Tag;
 import java.util.*;

/**
 * Auto generate by "Python tools"
 * 
 * @Date 2017-08-14 00:25:41
 */
 public class WuJiangLevel extends Sample{
    public static SampleFactory<WuJiangLevel> factory = new SampleFactoryImpl<>();
    public static WuJiangLevel getWuJiangLevel(int sid) {
        return factory.getSample(sid);
    }

    public static WuJiangLevel newWuJiangLevel(int sid) {
        return factory.newSample(sid);
    }
 	@Tag(3)
	// 武将ID
	public int wujiangSid;
	@Tag(4)
	// 武将等级
	public int level;
	@Tag(5)
	// 武将贴图
	public String spriteTexture;
	@Tag(6)
	// 武将技能
	public int skillId;

 }
