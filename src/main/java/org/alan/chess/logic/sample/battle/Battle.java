
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
 public class Battle extends Sample{
    public static SampleFactory<Battle> factory = new SampleFactoryImpl<>();
    public static Battle getBattle(int sid) {
        return factory.getSample(sid);
    }

    public static Battle newBattle(int sid) {
        return factory.newSample(sid);
    }
 	@Tag(3)
	// 战斗类型
	public int type;
	@Tag(4)
	// 战斗角色数量
	public int roleNum;
	@Tag(5)
	// 使用地图ID
	public int mapId;
	@Tag(6)
	// 棋盘行数
	public int row;
	@Tag(7)
	// 棋盘列数
	public int cell;

 }
