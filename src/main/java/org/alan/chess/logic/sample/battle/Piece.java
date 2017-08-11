
 package org.alan.chess.logic.sample.battle;

 import com.dyuproject.protostuff.Tag;
import org.alan.chess.logic.core.Sample;
import org.alan.chess.logic.core.SampleFactory;
import org.alan.chess.logic.core.SampleFactoryImpl;

/**
 * Auto generate by "Python tools"
 * 
 * @Date 2017-08-11 18:37:23
 */
 public class Piece extends Sample{
    public static SampleFactory<Piece> factory = new SampleFactoryImpl<>();
    public static Piece getPiece(int sid) {
        return factory.getSample(sid);
    }

    public static Piece newPiece(int sid) {
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

 }
