package org.alan.chess.logic.battle.sprite;

import org.alan.chess.logic.battle.BattlePoint;
import org.alan.chess.logic.sample.battle.CardSprite;

/**
 * author Alan
 * eamil mingweiyang@foxmail.com
 * date 2017/8/13
 */
public class XiangController extends SpriteController {
    public XiangController(CardSprite sprite) {
        super(sprite);
    }

    @Override
    public boolean canMove(BattlePoint point) {
        return false;
    }
}
