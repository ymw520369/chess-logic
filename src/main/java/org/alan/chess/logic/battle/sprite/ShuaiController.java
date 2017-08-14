package org.alan.chess.logic.battle.sprite;

import org.alan.chess.logic.battle.BattlePoint;

/**
 * author Alan
 * eamil mingweiyang@foxmail.com
 * date 2017/8/13
 */
@SpriteScript(SpriteType.SHUAI)
public class ShuaiController extends SpriteController {

    @Override
    public boolean moveTo(BattlePoint point) {
        return false;
    }
}
