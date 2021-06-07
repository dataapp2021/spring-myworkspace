package com.gdg.springmyworkspace.todo;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

	// GET 프로토콜://서버주소:포트/todos
	@GetMapping(value = "/todos")
	public List<Todo> getTodoList() {
		// SELECT 문을 실행해서 결과를 List 타입으로 변환
//		List<Todo> todoList = repo.findAll();
//		return todoList;

		return repo.findAll();
	}

	// 1건 추가
	// POST /todos {memo:"Spring 공부하기"}
	@PostMapping(value = "/todos")
	public Todo addTodo(@RequestBody Todo todo, HttpServletResponse res) {

		// 메모가 빈 값이면, 400 에러처리
		// 데이터를 서버에서 처리하는 양식에 맞게 보내지 않았음.
		if (todo.getMemo() == null || todo.getMemo().equals("")) {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

		// 데이터 검증(validation)과 소독(sanitization)
		// 클라이언트에서 넘오는 데이터에 대해서 점검
		todo.setCreatedTime(new Date().getTime());

		// repository.save(entity)
		// @Id: 필드 값이 없으면 INSERT, 있으면 UPDATE
		return repo.save(todo);
	}

	// 1건 조회
	// GET /todos/1 -> todo목록에서 id가 1인 레코드 조회
//	@GetMapping(value = "/todos/{id}")
	@RequestMapping(method = RequestMethod.GET, value = "/todos/{id}")
	public Todo getTodo(@PathVariable int id, HttpServletResponse res) {

		// SELECT ... FROM todo WHERE id = ?
		// null-safe한 방법으로 객체를 처리하게 함
		Optional<Todo> todo = repo.findById(id);

		// 리소스가 없으면 404 에러를 띄워줌
		if (todo.isEmpty()) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		return todo.get();
//		return todo.orElse(null);
	}

	// 1건 삭제
	// DELETE /todos/1 -> todo목록에서 id가 1인 레코드 삭제
	@DeleteMapping(value = "/todos/{id}")
	public boolean removeTodo(@PathVariable int id, HttpServletResponse res) {

		Optional<Todo> todo = repo.findById(id);

		// 리소스가 없으면 404 에러를 띄워줌
		if (todo.isEmpty()) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return false;
		}

		// soft-delete: 특정 컬럼을 업데이트하여 조회할 때 안 보이게함.
		// hard-delete: 실제로 DELETE 문으로 레코드를 삭제
		// JPA repository는 기본적으로 hard-delete를 사용한다.
		repo.deleteById(id);
		return true;
	}

	// 1건 수정
	// PUT /todo/1 {"memo":"JPA 공부하기"}
	@PutMapping(value = "/todos/{id}")
	public Todo modifyTodo(@PathVariable int id, @RequestBody Todo todo, HttpServletResponse res) {

		// 특정 필드만 업데이트 해야함
		// ex) 이전에 입력한 시스템 필드는 변경하면 안 됨.

		// 기존데이터 조회 후 변경된 데이터만 설정한 다음에 save

		Optional<Todo> findedTodo = repo.findById(id);

		// 리소스가 없으면 404 에러를 띄워줌
		if (findedTodo.isEmpty()) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		// 조회한 데이터에서 변경할 필드만 수정
		Todo toUpdateTodo = findedTodo.get(); // 기존 데이터
		toUpdateTodo.setMemo(todo.getMemo()); // 변경할 필드

		// repository.save(entity)
		// id값 제외하고 전체 필드를 업데이트함
		return repo.save(toUpdateTodo);
	}
}
