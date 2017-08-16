package org.alan.chess.logic.dao;

import org.alan.chess.logic.data.Player;
import org.alan.mars.mongo.MarsMongoDao;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Created on 2017/8/2.
 *
 * @author Alan
 * @since 1.0
 */
public abstract class PlayerDao extends MarsMongoDao<Player, Long> {
    public PlayerDao(MongoTemplate mongoTemplate) {
        super(Player.class, mongoTemplate);
    }

    public abstract Player findPlayerByUserId(int zoneId, long userId);

    public abstract Player createPlayer(int zoneId, long userId);
}
