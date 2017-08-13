package org.alan.chess.logic.battle.sprite;

import org.alan.chess.logic.battle.BattlePoint;
import org.alan.chess.logic.sample.battle.CardSprite;

/**
 * Created on 2017/8/11.
 *
 * @author Alan
 * @since 1.0
 */
public abstract class SpriteController {
    public CardSprite sprite;

    public abstract boolean canMove(BattlePoint point);

    public abstract void moveTo(BattlePoint point);
}
