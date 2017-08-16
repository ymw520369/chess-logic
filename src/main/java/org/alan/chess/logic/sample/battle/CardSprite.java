
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
 * @Date 2017-08-17 01:14:53
 */
 @ProtobufMessage
 public class CardSprite extends Sample{
    public static SampleFactory<CardSprite> factory = new SampleFactoryImpl<>();
    public static CardSprite getCardSprite(int sid) {
        return factory.getSample(sid);
    }

    public static CardSprite newCardSprite(int sid) {
        return factory.newSample(sid);
    }
 	@Tag(3)
	// 队伍
	public int team;
	@Tag(4)
	// 类型
	public int type;
	@Tag(5)
	// X位置
	public int x;
	@Tag(6)
	// Z位置
	public int z;
	@Tag(7)
	// 卡牌贴图
	public String bg;
	@Tag(8)
	// 角色贴图
	public String texture;
	@Tag(9)
	// 移动消耗的行动力
	public int needSp;

 }
