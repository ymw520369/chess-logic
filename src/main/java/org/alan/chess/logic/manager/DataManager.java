package org.alan.chess.logic.manager;

import org.alan.chess.logic.config.GameConfig;
import org.alan.chess.logic.constant.GameResultEnum;
import org.alan.chess.logic.constant.RedisKey;
import org.alan.chess.logic.controller.PlayerController;
import org.alan.chess.logic.dao.RoleDao;
import org.alan.chess.logic.data.Player;
import org.alan.chess.logic.data.Role;
import org.alan.chess.logic.data.UserInfo;
import org.alan.mars.protostuff.PFSession;
import org.alan.mars.uid.UidCacheManager;
import org.alan.mars.uid.UidTypeEnum;
import org.alan.utils.StringUtils;
import org.alan.utils.text.TextValidity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

/**
 * author Alan
 * eamil mingweiyang@foxmail.com
 * date 2017/8/16
 */
@Component
public class DataManager {

    @Resource(name = "centerRedisTemplate")
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private GameConfig gameConfig;

    @Autowired
    private TextValidity textValidity;

    @Autowired
    private UidCacheManager uidCacheManager;

    @Autowired
    private RoleDao roleDao;

    public UserInfo vertifyAccount(String token, long userId, int zoneId) {
        UserInfo userInfo = (UserInfo) redisTemplate.opsForHash().get(RedisKey.USER_INFO, userId);
        if (userInfo != null) {
            //登录信息认证通过
            if (token.equals(userInfo.token) && userId == userInfo.userId) {
                return userInfo;
            }
        }
        return null;
    }

    public Role createRole(int zoneId, long userId, String roleName) {
        Role role = roleDao.findByUserId(zoneId, userId);
        if (role == null && checkAddLockRoleName(roleName) == GameResultEnum.SUCCESS) {
            long roleUid = uidCacheManager.getUid(UidTypeEnum.ROLE_UID);
            role = new Role();
            role.name = roleName;
            role.roleUid = roleUid;
            role.userId = userId;
            role.zoneId = zoneId;
            roleDao.save(role);
            redisTemplate.opsForSet().remove(RedisKey.ROLE_NAME_KEY, roleName);
        }
        return role;
    }

    public GameResultEnum checkAddLockRoleName(String roleName) {
        //1、验证字符长度合法
        if (StringUtils.getStringByteLength(roleName) > gameConfig.roleNameLength) {
            return GameResultEnum.LENGTH_TOO_LONG;
        }
        //2、验证是否包含非法字符
        if (textValidity.validIllegalChar(roleName)) {
            return GameResultEnum.ILLEGAL;
        }
        synchronized (redisTemplate) {
            Set<String> lockRoleNameSet = redisTemplate.opsForSet().members(RedisKey.ROLE_NAME_KEY);
            //3、验证是否重复
            if (roleDao.existRoleName(roleName) || lockRoleNameSet.contains(roleName)) {
                return GameResultEnum.ROLE_REPEAT;
            }
            //4、锁定该名字，在使用成功以后从锁定表中删除
            redisTemplate.opsForSet().add(RedisKey.ROLE_NAME_KEY, roleName);
        }
        return GameResultEnum.SUCCESS;
    }

    public PlayerController bindPlayer(Role role, UserInfo userInfo, PFSession session) {
        Player player = new Player();
        player.role = role;
        player.userInfo = userInfo;
        PlayerController playerController = new PlayerController(session, player);
        session.setReference(playerController);
        return playerController;
    }
}
