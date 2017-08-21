package org.alan.chess.logic.data;

import org.alan.mars.data.UserInfo;
import org.alan.mars.protostuff.ProtobufMessage;

/**
 * Created on 2017/8/4.
 *
 * @author Alan
 * @since 1.0
 */
@ProtobufMessage
public class Player {
    public Role role;
    public transient UserInfo userInfo;
    public int sceneId;
}
