
 package org.alan.chess.logic.sample.scene;

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
 public class Room extends Sample{
    public static SampleFactory<Room> factory = new SampleFactoryImpl<>();
    public static Room getRoom(int sid) {
        return factory.getSample(sid);
    }

    public static Room newRoom(int sid) {
        return factory.newSample(sid);
    }
 	@Tag(3)
	// 场景类型
	public int sceneType;
	@Tag(4)
	// 房间允许人数
	public int maxNum;
	@Tag(5)
	// 对应战斗类型ID
	public int battleSid;

 }
