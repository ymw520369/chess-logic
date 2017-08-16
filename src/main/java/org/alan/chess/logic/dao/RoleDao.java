package org.alan.chess.logic.dao;

import org.alan.chess.logic.data.Role;
import org.alan.mars.mongo.MarsMongoDao;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * author Alan
 * eamil mingweiyang@foxmail.com
 * date 2017/8/16
 */
public abstract class RoleDao extends MarsMongoDao<Role, Long> {

    public RoleDao(MongoTemplate mongoTemplate) {
        super(Role.class, mongoTemplate);
    }

    public abstract Role findByUserId(int zoneId, long userId);

    public abstract boolean existRole(long roleUid);

    public abstract boolean existRoleName(String roleName);
}
