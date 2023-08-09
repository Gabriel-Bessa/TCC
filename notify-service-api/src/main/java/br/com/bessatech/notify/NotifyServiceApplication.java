package br.com.bessatech.notify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisKeyValueAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication(scanBasePackages = "br.com.bessatech.notify")
@EnableRedisRepositories(enableKeyspaceEvents = RedisKeyValueAdapter.EnableKeyspaceEvents.ON_STARTUP)
public class NotifyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotifyServiceApplication.class, args);
	}

}