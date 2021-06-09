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

	// SELECT * FROM todo WHERE memo = '�Ű�����'
	Page<Todo> findByMemo(Pageable page, String memo);

	// SELECT * FROM todo WHERE memo LIKE '%�Ű�����%';
	Page<Todo> findByMemoContaining(Pageable page, String memo);
}
