package com.gdg.springmyworkspace.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
	// CORS(cross origin resource sharing)을 설정
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// 모든 원격지에 대하여 모든 리소스와 메서드를 허용
		registry.addMapping("/**").allowedMethods("*");
	}
}
