package com.gdg.springmyworkspace.todo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoController {

	private TodoRepository repo;

	// @Autowired : 객체를 Spring IoC 컨테이너에서 생성하여 주입함
	// DI(Dependency Injection): 의존성 주입
	// 의존성 주입: 객체를 사용하는 곳이 아닌 외부에서 객체를 생성하여 주입하여 주는 것

	// TodoController 인스턴스는 Spring IoC 컨테이너에서 생성함
	// TodoController 객체를 생성하는 시점에 TodoRepository 인터페이스에 맞는 객체를 생성 후 주입함

	// TodoRepository 인터페이스에 맞는 SQL 문이 실행가능한 객체를 생성함

	// Entity-Repository: JPA(Java Persistence API)
	// Persistence: 영속화 -> 메모리에 있는 객체를 디스크 또는 데이터베이스 같이 비휘발성 장치에 저장
	// 내부적으로는 Hibernate 프레임워크 사용

	@Autowired
	public TodoController(TodoRepository repo) {
		this.repo = repo;
	}

	@GetMapping(value = "/todos")
	public List<Todo> getTodoList() {
		// SELECT 문을 실행해서 결과를 List 타입으로 변환
//		List<Todo> todoList = repo.findAll();
//		return todoList;

		return repo.findAll();
	}
}
