/**
 * Copyright Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 * <p>
 * 2017年3月2日
 */
package org.alan.chess.logic.dao.impl;

import org.alan.chess.logic.data.Role;
import org.alan.chess.logic.dao.RoleDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

/**
 * @author alan
 * @since 1.0
 */
@Component
public class RoleDaoImpl extends RoleDao {

    Logger log = LoggerFactory.getLogger(getClass());

    public RoleDaoImpl(@Autowired MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

    @Override
    public Role save(Role role) {
        long beginTime = System.currentTimeMillis();
//        log.info("开始保存玩家数据,roleUid={}", role.getRoleUid());
        Role r = super.save(role);
        long endTime = System.currentTimeMillis();
        log.info("玩家数据保存完成,roleUid={},useTime={}(ms)", role.roleUid, endTime - beginTime);
        return r;
    }

    @Override
    public Role findByUserId(int zoneId, long userId) {
        log.debug("find role, userid is {},zone is {}", userId, zoneId);
        return mongoTemplate.findOne(
                Query.query(Criteria.where("userUid").is(userId)), Role.class);
    }

    @Override
    public boolean existRole(long roleUid) {
        log.debug("exist role by role uid, roleUid is {}", roleUid);
        return mongoTemplate.exists(
                Query.query(Criteria.where("roleUid").is(roleUid)), Role.class);
    }

    @Override
    public boolean existRoleName(String roleName) {
        log.debug("check role name exist, role name is  {}", roleName);
        Criteria criteria = Criteria.where("roleName").is(roleName);
        Query query = new Query(criteria);
        return mongoTemplate.exists(query, Role.class);
    }
}
