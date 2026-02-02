package com.sajidtech.easytrip.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        // minimum threads always ready
        executor.setCorePoolSize(5);

        // maximum threads allowed
        executor.setMaxPoolSize(10);

        // queue size when all threads are busy
        executor.setQueueCapacity(100);

        // thread name prefix (for debugging)
        executor.setThreadNamePrefix("EasyTrip-Async-");

        executor.initialize();
        return executor;
    }
}
