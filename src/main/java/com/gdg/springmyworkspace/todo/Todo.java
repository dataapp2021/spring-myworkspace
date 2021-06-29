package com.gdg.springmyworkspace.todo;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.Data;

// Aggregation Root 객체
// 집합 뿌리 객체

// 예) Order 주문정보
// 예) OrderDetail, OrderLineTime: 주문상세, 주문 제품목록
@Data
@Entity
public class Todo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String memo;
	private Long createdTime;

	// JSON 으로 변환할 때 보여주지 않음.
//	@JsonIgnore
	// OneToMany(Order-OrderDetail), OneToOne(User-Profile)
	// EAGER: 상위객체 읽을 때 하위 객체 같이 읽음
	// LAZY: 하위객체를 사용할 때 하위 객체를 읽어옴. 기본 페치전략
//	@OneToMany(fetch = FetchType.LAZY)
	@OneToMany
	// ManyToMany(JoinColumn 쓰면 안 됨)
	@JoinColumn(name = "todoId") // todoId(필드명), todo_id(컬럼명)
	private List<TodoComment> comments;
}
