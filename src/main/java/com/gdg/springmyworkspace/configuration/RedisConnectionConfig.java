package com.gdg.springmyworkspace.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

// @Configuration 어노테이션은 @Bean 타입의 의존성 객체를 선언할 수 있음
// @Bean이 붙은 메서드의 리턴 객체는 스프링 IoC 컨테이너에 의해서 생성되고 관리됨.
// @Autowired에 @Bean 리턴 객체 타입과 같은 것이 있으면 의존성 주입함.

@Configuration
public class RedisConnectionConfig {

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		redisStandaloneConfiguration.setHostName("127.0.0.1");
		redisStandaloneConfiguration.setPort(6379);
		LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);
		return connectionFactory;
	}

}
