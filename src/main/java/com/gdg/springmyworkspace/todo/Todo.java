package com.gdg.springmyworkspace.todo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

// @Entity = 데이터베이스의 테이블과 연결함(mapping)
// ORM(Object Relational Mapping)
// : 객체와 테이블을 맵핑한다.

// class와 테이블은 pascal-case -> snake-case로 맵핑
// Todo class -> todo table
// StudentInfo class -> student_info table

// 필드와 컬럼은 camel-case -> snake-case로 맵핑
// createdTime field -> created_time column

// 코드 설계에 따라서 데이터베이스 구조가 만들어지는 방법
// auto-migration

@Data
@Entity
public class Todo {

	// @Id -> 테이블의 PK(유일/대표 컬럼)
	@Id
	// @GeneratedValue -> 필드 값 생성 방법 정의, IDENTITY는 데이터베이스의 자동증가값을 사용
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String memo;
	private Long createdTime;
}
