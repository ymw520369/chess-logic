/*
 * Copyright (c) 2017. Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 */

package org.alan.chess.logic.battle;

import org.alan.miner.pbm.BattleMessage.GameInput;
import org.alan.miner.pbm.BattleMessage.ResGameInit;
import org.alan.miner.pbm.BattleMessage.ResGameInputs;
import org.alan.miner.pbm.BattleMessage.ResGameStart;
import org.alan.miner.pbm.DataObject.RoleShowInfo;

import java.util.List;

/**
 * Created on 2017/4/26.
 *
 * @author Alan
 * @since 1.0
 */
public class BattleMessageHelper {
    public void sendGameInit(BattleController battleController,int seed) {
//        Battle battle = battleController.getSource();
        ResGameInit.Builder builder = ResGameInit.newBuilder().setBattleSid(0)
                .setBattleUid(battleController.getUid())
                .setMapSid(0)
                .setSeed(seed);
        battleController.getFighters().forEach(e ->
                builder.addRoles(RoleShowInfo.newBuilder().setRoleName(e.getName()).setRoleUid(e.getUid()))
        );
        battleController.broadcast(builder.build());
    }

    public void sendGameStart(BattleController battleController) {
        ResGameStart resGameStart = ResGameStart.getDefaultInstance();
        battleController.broadcast(resGameStart);
    }

    public void broadcastGameInput(BattleController battleController, int lfs, List<GameInput> inputs) {
        ResGameInputs resGameInputs = ResGameInputs.newBuilder().setLfs(lfs).addAllInput(inputs).build();
        battleController.broadcast(resGameInputs);
    }
}
