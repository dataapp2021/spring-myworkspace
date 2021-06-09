package com.gdg.springmyworkspace.todo;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

//		return repo.findAll();
//		SELECT * FROM todo ORDER BY id DESC; -- 역순(descending) 정렬
		return repo.findAll(Sort.by("id").descending());

	}

	// ?key=value&key=value <- 매개변수를 Query String(질의 문자열)
	// HTTP GET 메서드로 데이터를 조회할 때 매개변수를 넘기는 방법
	// Spring에서는 @RequestParam 어노테이션으로 해당 매개변수를 받는다.

	// page: 몇번째 페이지인지, size: 페이지당 몇건의 데이터인지
	// GET /todos/paging?page=0&size=10
	// GET /todos/paging?page=1&size=10
	@GetMapping(value = "/todos/paging")
	public Page<Todo> getTodoListPaging(@RequestParam int page, @RequestParam int size) {

		/* 0번째 페이지 */
//		-- LIMIT 개수
//		-- 앞에서 개수만큼의 레코드만 조회함
//		select * from todo order by id desc;
//		select * from todo order by id desc LIMIT 10;		

		/* 0번째 페이지 이후부터 JPA에서 처리하는 방법 */
//		-- LIMIT 건너띄기할 개수, 조회 개수
//		-- LIMIT 10, 10
//		-- page: 1, size: 10
//		select * from todo order by id desc LIMIT 10, 10;				
//		-- LIMIT 20, 10
//		-- page: 2, size: 10
//		select * from todo order by id desc LIMIT 20, 10;		

		/* 0번째 페이지 이후부터 OFFSET으로 처리하는 방법 */
//		-- LIMIT 조회 개수 OFFSET 건너띄기할 개수
//		-- LIMIT 10 OFFSET 10: 10개 건너띄고 다음 10개 조회
//		-- page: 1, size: 10
//		select * from todo order by id desc LIMIT 10 OFFSET 10;				
//		-- LIMIT 10 OFFSET 20: 20개 건너띄고 다음 10개 조회
//		-- page: 2, size: 10
//		select * from todo order by id desc LIMIT 10 OFFSET 20;		

		/* 전체건수 조회 */
//		-- COUNT(컬럼)
//		-- 컬럼에는 clustered index 컬럼이 아니면 성능이 떨어짐
//		-- 잘모르면 *를 써라, MySQL에서 알아서 최적화된 방법으로 조회함.
//		-- 전체 레코드 개수를 조회
//		select count(id) from todo;
//		select count(*) from todo;		

		return repo.findAll(PageRequest.of(page, size, Sort.by("id").descending()));
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

		// 데이터를 수정할 때는 특정 필드만 업데이트 해야함
		// ex) 이전에 입력한 시스템 필드는 변경하면 안 됨.

		// repository.save(entity)
		// id값 제외하고 전체 필드를 업데이트함

		/* -- 기존데이터 조회 후 변경된 데이터만 설정한 다음에 save --- */

		// 1. 기존 데이터 조회
		Optional<Todo> findedTodo = repo.findById(id);

		// 2. (요청데이터 검증1) id에 해당하는 리소스가 없으면 404 에러를 띄워줌
		if (findedTodo.isEmpty()) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		// 3. (요청데이터 검증2) memo 필드가 빈값이면 400에러를 띄워줌
		if (todo.getMemo() == null && todo.getMemo().equals("")) {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

		// 4. 데이터베이스에서 읽어온 기존 데이터에 변경할 필드만 수정함
		Todo toUpdateTodo = findedTodo.get();
		toUpdateTodo.setMemo(todo.getMemo());

		// 5. 레코드 update
		return repo.save(toUpdateTodo);
	}
}
