
 package org.alan.chess.logic.sample.battle;

 import org.alan.mars.sample.Sample;
 import org.alan.mars.sample.SampleFactory;
 import org.alan.mars.sample.impl.SampleFactoryImpl;
 import com.dyuproject.protostuff.Tag;
 import java.util.*;

/**
 * Auto generate by "Python tools"
 * 
 * @Date 2017-08-15 01:12:35
 */
 public class Skill extends Sample{
    public static SampleFactory<Skill> factory = new SampleFactoryImpl<>();
    public static Skill getSkill(int sid) {
        return factory.getSample(sid);
    }

    public static Skill newSkill(int sid) {
        return factory.newSample(sid);
    }
 	@Tag(3)
	// 技能描述
	public String des;
	@Tag(4)
	// 技能规则脚本
	public String script;

 }
