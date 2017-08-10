/*
 * Copyright (c) 2017. Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 */

package org.alan.chess.logic.battle;

import org.alan.mars.net.MarsSession;
import org.alan.mars.proxy.annotation.ProxyMapping;
import org.alan.miner.data.role.RoleController;
import org.alan.miner.ds.helper.SendMessageHelper;
import org.alan.miner.pbm.BattleMessage;
import org.alan.miner.pbm.BattleMessage.ReqGameInitDone;
import org.alan.miner.pbm.BattleMessage.ReqGameInput;
import org.alan.miner.pbm.BattleMessage.ResReviveResult;
import org.alan.miner.pbm.DataObject.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created on 2017/3/20.
 *
 * @author Alan
 * @since 1.0
 */
@Component
@ProxyMapping(BattleMessage.ReqKill.class)
public class BattleMessageHandler {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private SendMessageHelper messageHelper;

    @Autowired
    private BattleService service;

    @Autowired
    private BattleManager battleManager;

    /**
     * 战斗中击杀玩家
     */
    @ProxyMapping(BattleMessage.ReqKill.class)
    public void kill(RoleController roleController) {
        service.kill(roleController.getRole());
    }

    /**
     * 战斗中挖矿
     */
    @ProxyMapping(BattleMessage.ReqMining.class)
    public void mining(RoleController roleController) {//
        service.mining(roleController.getRole());
    }

    /**
     * /战斗开始
     */
    @ProxyMapping(BattleMessage.ReqBattleBegin.class)
    public void battleBegin(RoleController roleController) {
        service.battleBegin(roleController.getRole());
    }

    /**
     * 战斗中请求复活
     */
    @ProxyMapping(BattleMessage.ReqRevive.class)
    public void revive(MarsSession session, RoleController roleController) { //
        if (service.revive(roleController.getRole())) {
            sendReviveResult(session, Result.SUCCESS);
            messageHelper.sendRoleCurrency(roleController);
            return;
        }
        sendReviveResult(session, Result.INSUFFICIENT_DIAMOND);
    }

    @ProxyMapping(ReqGameInitDone.class)
    public void gameInitDone(RoleController roleController) {
        BattleController battleController = battleManager.find(roleController.getSceneUid());
        if (battleController == null) {
            log.warn("找不到玩家的战斗信息,roleUid={}", roleController.getRoleUid());
        } else {
            battleController.initDone(roleController.getRoleUid());
        }
    }

    @ProxyMapping(BattleMessage.ReqGameInput.class)
    public void input(RoleController roleController, ReqGameInput reqGameInput) {
        BattleController battleController = battleManager.find(roleController.getSceneUid());
        if (battleController == null) {
            log.warn("找不到玩家的战斗信息,roleUid={}", roleController.getRoleUid());
        } else {
            battleController.input(roleController.getRoleUid(), reqGameInput.getInput());
        }
    }

//    @ProxyMapping(BattleMessage.ReqBattleEnd.class)
//    public void battleEnd(BattleMessage.ReqBattleEnd battleEnd,RoleController roleController){
//        BattleEnd endDto = new BattleEnd();
//        endDto.setRoleUid(roleController.getRoleUid());
//        endDto.setGold(battleEnd.getGold());
//        endDto.setKillNum(battleEnd.getKillNum());
//        endDto.setRanking(battleEnd.getRanking());
//        endDto.setScore(battleEnd.getScore());
//        service.battleEnd(endDto);
//        messageHelper.sendBattleEndResult(roleController.getSession());
//    }

    public void sendReviveResult(MarsSession session, Result result) {
        ResReviveResult resReviveResult = ResReviveResult
                .newBuilder().setResult(result).build();
        session.send(resReviveResult);
    }

//    @Override
//    public int getMessageType() {
//        return BattleMessage.ReqKill.getDefaultInstance().getMessageType();
//    }

//    @Override
//    public void handle(MarsSession session, PbMessage.TXMessage msg) {
//        ByteString bs = msg.getDataMessage();
//        int cmd = msg.getCmd();
//        if (log.isDebugEnabled()) {
//            log.debug("prop message received {} bytes", bs.size());
//        }
//        RoleController roleController = session.getReference(RoleController.class);
//        try {
//            if (cmd == BattleMessage.ReqKill.getDefaultInstance().getCmd()) {
//                service.kill(roleController.getRole());
//            }
//            if (cmd == BattleMessage.ReqMining.getDefaultInstance().getCmd()) {
//                service.mining(roleController.getRole());
//            }
//            if (cmd == BattleMessage.ReqBattleBegin.getDefaultInstance().getCmd()) {
//                service.battleBegin(roleController.getRole());
//            }
//            if (cmd == BattleMessage.ReqRevive.getDefaultInstance().getCmd()) {
//                if (service.revive(roleController.getRole())) {
//                    sendReviveResult(session, Result.SUCCESS);
//                    messageHelper.sendRoleCurrency(roleController);
//                    return;
//                }
//                sendReviveResult(session, Result.INSUFFICIENT_DIAMOND);
//            }
//        } catch (Exception e) {
//            log.warn("battle message cmd {} handle error.", cmd, e);
//        }
//    }


}
