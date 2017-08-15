package org.alan.chess.logic.dao;

import org.alan.chess.logic.bean.Role;

/**
 * Created on 2017/8/2.
 *
 * @author Alan
 * @since 1.0
 */
public interface PlayerDao {
    Role findRoleByUserId(int zoneId,long userId);

    Role createRole(int zoneId,long userId);
}