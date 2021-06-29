package com.gdg.springmyworkspace.todo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

@Repository
@RestResource(path = "todo-comments")
public interface TodoCommentRepository extends JpaRepository<TodoComment, Integer> {

}
