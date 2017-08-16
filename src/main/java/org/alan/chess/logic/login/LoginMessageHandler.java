package org.alan.chess.logic.login;

import org.alan.chess.logic.controller.PlayerController;
import org.alan.chess.logic.data.Role;
import org.alan.chess.logic.data.UserInfo;
import org.alan.chess.logic.dao.RoleDao;
import org.alan.chess.logic.manager.DataManager;
import org.alan.chess.logic.manager.LogicManager;
import org.alan.mars.protostuff.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private LogicManager logicManager;

    @Autowired
    private DataManager dataManager;


    @Command(1)
    public void vertifyAccount(PFSession session, VertifyUserInfo vertifyInfo) {
        UserInfo userInfo = dataManager.vertifyAccount(vertifyInfo.token, vertifyInfo.userId, vertifyInfo.zoneId);
        if (userInfo != null) {
            Role role = roleDao.findByUserId(vertifyInfo.zoneId, vertifyInfo.userId);
            if (role == null) {
                sendCreateRole(session);
            } else {
                PlayerController playerController = dataManager.bindPlayer(role, userInfo, session);
                logicManager.enterGame(playerController);
            }
        }
    }

    @Command(2)
    public void createRole(PFSession session, ReqCreateRole reqCreateRole) {
        VertifyUserInfo vertifyInfo = reqCreateRole.vertifyUserInfo;
        UserInfo userInfo = dataManager.vertifyAccount(vertifyInfo.token, vertifyInfo.userId, vertifyInfo.zoneId);
        if (userInfo != null) {
            Role role = dataManager.createRole(reqCreateRole.vertifyUserInfo.zoneId,
                    reqCreateRole.vertifyUserInfo.userId, reqCreateRole.name);
            if (role != null) {
                PlayerController playerController = dataManager.bindPlayer(role, userInfo, session);
                logicManager.enterGame(playerController);
            }
        }
    }

    public void sendCreateRole(PFSession session) {
        session.send(new CreateRole());
    }


    @RequestMessage
    @ProtobufMessage
    public static class VertifyUserInfo {
        public String token;
        public long userId;
        public int zoneId;
    }

    @RequestMessage
    @ProtobufMessage
    public static class ReqCreateRole {
        public VertifyUserInfo vertifyUserInfo;
        public String name;
    }

    @ResponseMessage(messageType = 1000, cmd = 1)
    @ProtobufMessage
    public static class CreateRole {
        public boolean success;
    }
}
