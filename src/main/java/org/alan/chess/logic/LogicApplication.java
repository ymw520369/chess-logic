package org.alan.chess.logic;

import org.alan.mars.MarsContext;
import org.alan.mars.cluster.ClusterSystem;
import org.alan.mars.cluster.SessionMessageHandler;
import org.alan.mars.config.NodeConfig;
import org.alan.mars.netty.NettyServer;
import org.alan.mars.protostuff.MarsMessageChannelInitializer;
import org.alan.mars.protostuff.MarsMessageDispatcher;
import org.alan.mars.timer.TimerCenter;
import org.alan.mars.uid.UidCacheManager;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.Order;

/**
 * Created on 2017/8/2.
 *
 * @author Alan
 * @since 1.0
 */
@SpringBootApplication
@ComponentScan("org.alan")
@Order(value = 999)
public class LogicApplication implements CommandLineRunner {
    /**
     * 节点配置信息
     */
    private final NodeConfig nodeConfig;
    /**
     * 定时中心
     */
    private TimerCenter timerCenter;
    /**
     * 文件监视器
     */
    private FileAlterationMonitor monitor;

    private ClusterSystem clusterSystem;

    @Autowired
    public LogicApplication(NodeConfig nodeConfig, ClusterSystem clusterSystem) {
        this.nodeConfig = nodeConfig;
        this.clusterSystem = clusterSystem;
    }

    public static void main(String[] args) {
        SpringApplication.run(LogicApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        MarsMessageDispatcher messageDispatcher = MarsContext
                .getBean(MarsMessageDispatcher.class);
        if (nodeConfig.isUseGate()) {
            clusterSystem.startClusterServer(nodeConfig.getTcpAddress());
        } else {
            MarsMessageChannelInitializer initializer = new MarsMessageChannelInitializer(messageDispatcher);
            int port = nodeConfig.getTcpAddress().getPort();
            NettyServer nettyServer = new NettyServer(port, initializer);
            nettyServer.setName("tcp-nio-" + port);
            nettyServer.start();
        }

        timerCenter.start();
        monitor.start();
    }

    @Bean
    public MarsMessageDispatcher createServerMessageDispatcher() {
        return new MarsMessageDispatcher();
    }

    @Bean("timerCenter")
    public TimerCenter createEventTimer() {
        return  timerCenter = new TimerCenter("timerCenter");
    }

    @Bean
    public FileAlterationMonitor createFileMonitor() {
        return monitor = new FileAlterationMonitor();
    }

    @Bean
    public UidCacheManager createUidCacheManager() {
        return new UidCacheManager(clusterSystem, true);
    }
}
