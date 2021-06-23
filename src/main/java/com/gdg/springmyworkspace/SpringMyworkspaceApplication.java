package com.gdg.springmyworkspace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

// @����: Annotation(������̼�, �ֳ����̼�)
// Ŭ������, �ʵ�, �޼��忡 ���� �� ����.
// ������̼��� �پ� �ִ� �ڵ带 Ư�� ������� �ڵ�ȭ �ϱ� ���� ���

// @Scheduled ������̼����� �޼��带 �����Ϸ��� �ʿ���
@EnableScheduling
@EnableCaching
@SpringBootApplication
public class SpringMyworkspaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMyworkspaceApplication.class, args);
	}

}
