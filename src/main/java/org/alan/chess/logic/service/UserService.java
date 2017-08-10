package org.alan.chess.logic.service;

import org.alan.chess.logic.bean.UserInfo;

/**
 * Created on 2017/8/4.
 *
 * @author Alan
 * @since 1.0
 */
public interface UserService {

    UserInfo findUserInfo(long userId);
}
