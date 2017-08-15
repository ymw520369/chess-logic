package org.alan.chess.logic.dao;

import org.alan.chess.logic.bean.UserInfo;

/**
 * Created on 2017/8/4.
 *
 * @author Alan
 * @since 1.0
 */
public interface UserDao {

    UserInfo findUserInfo(long userId);
}
