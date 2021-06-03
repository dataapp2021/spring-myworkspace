package com.gdg.springmyworkspace.todo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoController {

	private TodoRepository repo;

	// @Autowired : ��ü�� Spring IoC �����̳ʿ��� �����Ͽ� ������
	// DI(Dependency Injection): ������ ����
	// ������ ����: ��ü�� ����ϴ� ���� �ƴ� �ܺο��� ��ü�� �����Ͽ� �����Ͽ� �ִ� ��

	// TodoController �ν��Ͻ��� Spring IoC �����̳ʿ��� ������
	// TodoController ��ü�� �����ϴ� ������ TodoRepository �������̽��� �´� ��ü�� ���� �� ������

	// TodoRepository �������̽��� �´� SQL ���� ���డ���� ��ü�� ������

	// Entity-Repository: JPA(Java Persistence API)
	// Persistence: ����ȭ -> �޸𸮿� �ִ� ��ü�� ��ũ �Ǵ� �����ͺ��̽� ���� ���ֹ߼� ��ġ�� ����
	// ���������δ� Hibernate �����ӿ�ũ ���

	@Autowired
	public TodoController(TodoRepository repo) {
		this.repo = repo;
	}

	@GetMapping(value = "/todos")
	public List<Todo> getTodoList() {
		// SELECT ���� �����ؼ� ����� List Ÿ������ ��ȯ
//		List<Todo> todoList = repo.findAll();
//		return todoList;

		return repo.findAll();
	}
}
