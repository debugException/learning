package cn.ixuehui.learning;

import java.util.concurrent.Executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@EnableCaching //开启缓存注解
@EnableAsync //开启异步
public class AppApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}
	
	/**
	 * 使用自定义的异步线程池
	 * @Async 有两个使用的限制：
			它必须仅适用于public方法
			在同一个类中调用异步方法将无法正常工作（self-invocation）
	 * @return
	 */
	@Bean
	public Executor asyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(5);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("ixuehui-threadPool-");
		executor.initialize();
		return executor;
	}
}
