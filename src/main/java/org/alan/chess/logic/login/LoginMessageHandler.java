package org.alan.chess.logic.login;

import org.alan.chess.logic.bean.Role;
import org.alan.chess.logic.bean.UserInfo;
import org.alan.chess.logic.manager.LogicManager;
import org.alan.chess.logic.service.RoleService;
import org.alan.chess.logic.service.UserService;
import org.alan.mars.protostuff.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created on 2017/8/2.
 *
 * @author Alan
 * @since 1.0
 */
@Component
@MessageType(1000)
public class LoginMessageHandler {
    private Logger log = LoggerFactory.getLogger(getClass());

    private RoleService roleService;

    private UserService userService;

    private LogicManager logicManager;

    public UserInfo vertifyAccount(VertifyUserInfo vertifyInfo) {
        UserInfo userInfo = userService.findUserInfo(vertifyInfo.userId);
        if (userInfo != null) {
            //登录信息认证通过
            if (vertifyInfo.token.equals(userInfo.token) && vertifyInfo.userId == userInfo.userId) {
                return userInfo;
            }
        }
        return null;
    }

    @Command(1)
    public void vertifyAccount(PFSession session, VertifyUserInfo vertifyInfo) {
        UserInfo userInfo = vertifyAccount(vertifyInfo);
        if (userInfo != null) {
            Role role = roleService.findRoleByUserId(vertifyInfo.zoneId, vertifyInfo.userId);
            if (role == null) {
                sendCreateRole(session);
            } else {
                logicManager.EnterGame(session, role, userInfo);
            }
        }
    }

    @Command(2)
    public void createRole(PFSession session, ReqCreateRole reqCreateRole) {
        UserInfo userInfo = vertifyAccount(reqCreateRole.vertifyUserInfo);
        if (userInfo != null) {
            Role role = roleService.createRole(reqCreateRole.vertifyUserInfo.zoneId,
                    reqCreateRole.vertifyUserInfo.userId);
            logicManager.EnterGame(session, role, userInfo);
        }
    }

    public void sendCreateRole(PFSession session) {
        session.send(new CreateRole());
    }


    @RequestMessage
    public static class VertifyUserInfo {
        public String token;
        public long userId;
        public int zoneId;
    }

    @RequestMessage
    public static class ReqCreateRole {
        public VertifyUserInfo vertifyUserInfo;
        public String name;
    }

    @ResponseMessage(messageType = 1000, cmd = 1)
    public static class CreateRole {
    }
}
