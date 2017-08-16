package org.alan.chess.logic.dao.impl;

import org.alan.chess.logic.data.Player;
import org.alan.chess.logic.dao.PlayerDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * author Alan
 * eamil mingweiyang@foxmail.com
 * date 2017/8/16
 */
public class PlayerDaoImpl extends PlayerDao {
    Logger log = LoggerFactory.getLogger(getClass());

    public PlayerDaoImpl(@Autowired MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

    @Override
    public Player findPlayerByUserId(int zoneId, long userId) {
        log.debug("find role by user id, userid is {},zone is {}", userId, zoneId);
        Query query = Query.query(Criteria.where("userUid").is(userId).and("zoneId").is(zoneId));
        return mongoTemplate.findOne(query
                , Player.class);
    }

    @Override
    public Player createPlayer(int zoneId, long userId) {
        return null;
    }
}
