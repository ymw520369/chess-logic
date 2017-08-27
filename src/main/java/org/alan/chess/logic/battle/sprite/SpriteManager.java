package org.alan.chess.logic.battle.sprite;

import org.alan.chess.logic.sample.battle.CardSprite;
import org.alan.utils.ClassUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * author Alan
 * eamil mingweiyang@foxmail.com
 * date 2017/8/13
 */
@Component
public class SpriteManager implements CommandLineRunner {
    private static Map<Integer, Class<?>> spriteControllers = new HashMap<>();

    @Override
    public void run(String... args) throws Exception {
        spriteControllers.clear();
        Set<Class<?>> clazzs = ClassUtils.getAllClassByAnnotation("org.alan.chess.logic", SpriteScript.class);
        if (clazzs != null && !clazzs.isEmpty()) {
            clazzs.forEach(clazz -> {
                SpriteScript spriteScript = clazz.getAnnotation(SpriteScript.class);
                //此处做一个特殊处理，枚举下标是从0开始，配置中棋子是从1开始，所以这里做加1处理
                spriteControllers.put(spriteScript.value().ordinal() + 1, clazz);
            });
        }
    }

    public static SpriteController create(CardSprite sprite) {
        Class<?> clazz = spriteControllers.get(sprite.type);
        try {
            SpriteController spriteController = (SpriteController) clazz.newInstance();
            spriteController.sprite = (CardSprite) sprite.clone();
            return spriteController;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
