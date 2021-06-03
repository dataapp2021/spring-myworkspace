package com.gdg.springmyworkspace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @내용: Annotation(어노테이션, 애노테이션)
// 클래스나, 필드, 메서드에 붙일 수 있음.
// 어노테이션이 붙어 있는 코드를 특정 기능으로 자동화 하기 위한 기법
@SpringBootApplication
public class SpringMyworkspaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMyworkspaceApplication.class, args);
	}

}
