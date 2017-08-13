/*
 * Copyright (c) 2017. Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 */

package org.alan.chess.logic.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created on 2017/4/17.
 *
 * @author Alan
 * @since 1.0
 */
@Configuration
//@ConfigurationProperties(prefix = "game")
@PropertySource("file:config/logicConfig.json")
public class GameConfig {
    //复活需要钻石数
    public int reviveNeedDiamond;
    //改名需要钻石数
    public int renameNeedDiamond;
    //角色名长度
    public int roleNameLength;
}
