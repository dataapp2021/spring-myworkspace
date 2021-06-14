package com.gdg.springmyworkspace.todo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// ������ ���� �������̽� ����
// JpaRepository<��ƼƼŸ��, idŸ��>

// �������̽��� �ν��Ͻ� ������ �� �ȴ�.

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {

	// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation

	// SELECT * FROM todo WHERE memo = '�Ű�����'
	// SELECT COUNT(id) FROM todo
	Page<Todo> findByMemo(Pageable page, String memo);

	// SELECT * FROM todo WHERE memo LIKE '%�Ű�����%';
	// SELECT COUNT(id) FROM todo
	Page<Todo> findByMemoContaining(Pageable page, String memo);
}
