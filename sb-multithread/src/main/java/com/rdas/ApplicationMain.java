package com.rdas;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestOperations;

import java.util.concurrent.Executor;

@Slf4j
@SpringBootApplication
@EnableAsync
public class ApplicationMain {

    public static void main(String[] args) {
        log.debug("Number of processors: " + Runtime.getRuntime().availableProcessors());
        SpringApplication.run(ApplicationMain.class, args);
    }

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(50);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("GithubLookup-");
        executor.initialize();
        return executor;
    }

    @Bean
    public RestOperations rest(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.basicAuthentication("ranadas", "").build();
    }
}
