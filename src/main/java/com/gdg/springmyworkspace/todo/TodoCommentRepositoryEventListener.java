package com.gdg.springmyworkspace.todo;

import java.util.Date;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;

// Repository에서 엔티티 저장전, 이후 등과 같은 이벤트에 대한 처리를 추가할 수 있음.
@Configuration
public class TodoCommentRepositoryEventListener extends AbstractRepositoryEventListener<TodoComment> {

	@Override
	public void onBeforeCreate(TodoComment comment) {
		comment.setCreatedTime(new Date().getTime());
	}
}