
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
 * @Date 2017-08-27 11:36:23
 */
 @ProtobufMessage
 public class Scene extends Sample{
    public static SampleFactory<Scene> factory = new SampleFactoryImpl<>();
    public static Scene getScene(int sid) {
        return factory.getSample(sid);
    }

    public static Scene newScene(int sid) {
        return factory.newSample(sid);
    }
 	@Tag(3)
	// 场景类型
	public int sceneType;

 }
