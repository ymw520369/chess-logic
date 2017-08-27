package org.alan.chess.logic.config;

import com.alibaba.fastjson.JSON;
import org.alan.utils.FileHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * author Alan
 * eamil mingweiyang@foxmail.com
 * date 2017/8/27
 */
@Component
public class ConfigManager {
    public String logicConfigFile = "config/logicConfig.json";


    @Bean
    public LogicConfig logicConfig() {
        String context = FileHelper.readFile(logicConfigFile);
        return JSON.parseObject(context, LogicConfig.class);
    }
}
