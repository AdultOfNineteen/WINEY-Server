package com.example.wineycommon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig extends AsyncConfigurerSupport {

    @Bean(name = "badge")
    public Executor badgeThreadExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10); // 기본적으로 실행 대기 중인 Thread 개수
        executor.setMaxPoolSize(10); // 동시에 동작하는 최대 Thread 개수
        executor.setQueueCapacity(500); // CorePool이 초과될때 Queue에 저장했다가 꺼내서 실행된다. (500개까지 저장함)
        executor.setThreadNamePrefix("async-badge-thread"); // Spring에서 생성하는 Thread 이름의 접두사
        executor.initialize();
        return executor;
    }

    @Bean(name = "badge_provide_first_analysis")
    public Executor badgeFirstAnalysisThreadExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10); // 기본적으로 실행 대기 중인 Thread 개수
        executor.setMaxPoolSize(10); // 동시에 동작하는 최대 Thread 개수
        executor.setQueueCapacity(500); // CorePool이 초과될때 Queue에 저장했다가 꺼내서 실행된다. (500개까지 저장함)
        executor.setThreadNamePrefix("async-provide-badge-thread"); // Spring에서 생성하는 Thread 이름의 접두사
        executor.initialize();
        return executor;
    }

    @Bean(name = "connect_user")
    public Executor connectUserCheckThreadExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10); // 기본적으로 실행 대기 중인 Thread 개수
        executor.setMaxPoolSize(10); // 동시에 동작하는 최대 Thread 개수
        executor.setQueueCapacity(500); // CorePool이 초과될때 Queue에 저장했다가 꺼내서 실행된다. (500개까지 저장함)
        executor.setThreadNamePrefix("connect-user-badge-thread"); // Spring에서 생성하는 Thread 이름의 접두사
        executor.initialize();
        return executor;
    }
}