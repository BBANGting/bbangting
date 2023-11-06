package com.khu.bbangting.infra;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync //비동기 처리를 위한 기본 설정 제공
@Slf4j
public class AsynConfig implements AsyncConfigurer { //커스텀 설정 추가

    @Override
    public Executor getAsyncExecutor() { //스레드 풀 직접 지정
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        int processors = Runtime.getRuntime().availableProcessors();
        log.info("processor count {}", processors);
        executor.setCorePoolSize(processors);
        executor.setMaxPoolSize(processors * 2);
        executor.setQueueCapacity(50);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("AsynExecutor-");
        executor.initialize();
        return executor;
    }
}
