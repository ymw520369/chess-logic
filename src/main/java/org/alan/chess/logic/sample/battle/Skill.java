
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
