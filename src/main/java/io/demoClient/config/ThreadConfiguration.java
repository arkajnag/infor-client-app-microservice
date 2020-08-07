package io.demoClient.config;

import java.util.concurrent.Executor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class ThreadConfiguration {
	
	@Bean
    public Executor getThreadConfig(){
        ThreadPoolTaskExecutor executorService=new ThreadPoolTaskExecutor();
        executorService.setQueueCapacity(100);
        executorService.setMaxPoolSize(10);
        executorService.setCorePoolSize(5);
        executorService.setAllowCoreThreadTimeOut(true);
        executorService.setThreadNamePrefix("client-thread-");
        executorService.initialize();
        return executorService;
    }

}
