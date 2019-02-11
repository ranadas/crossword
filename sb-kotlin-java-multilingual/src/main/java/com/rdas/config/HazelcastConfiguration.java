package com.rdas.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@Order(1)
public class HazelcastConfiguration {

    //https://www.programcreek.com/java-api-examples/?code=hsj-xiaokang/springboot-shiro-cas-mybatis/springboot-shiro-cas-mybatis-master/cas-4.2.5/cas-server-integration-hazelcast/src/main/java/org/jasig/cas/ticket/registry/HazelcastTicketRegistry.java#
    @Bean
    public Config hazelCastConfig() {
        Config config = new Config();
        config.setInstanceName("hazelcast-instance")
                .addMapConfig(
                        new MapConfig()
                                .setName("configuration")
                                .setMaxSizeConfig(new MaxSizeConfig(200, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
                                .setEvictionPolicy(EvictionPolicy.LRU)
                                .setTimeToLiveSeconds(-1));
        return config;
    }

    @Bean(name = "crosswordCacheHzInstance")
    public HazelcastInstance hazelcast(@Autowired final Config hzConfig) {
        return Hazelcast.newHazelcastInstance(hzConfig);
    }
}
