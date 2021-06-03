package com.gdg.springmyworkspace.todo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// 데이터 접근 인터페이스 선언
// JpaRepository<엔티티타입, id타입>

// 인터페이스는 인스턴스 생성이 안 된다.

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {

}
