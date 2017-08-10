package org.alan.chess.logic.service;

import org.alan.chess.logic.bean.Role;

/**
 * Created on 2017/8/2.
 *
 * @author Alan
 * @since 1.0
 */
public interface RoleService {
    Role findRoleByUserId(int zoneId,long userId);

    Role createRole(int zoneId,long userId);
}
