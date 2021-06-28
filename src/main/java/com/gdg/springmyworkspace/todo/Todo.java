package com.gdg.springmyworkspace.todo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

// @Entity = �����ͺ��̽��� ���̺�� ������(mapping)
// ORM(Object Relational Mapping)
// : ��ü�� ���̺��� �����Ѵ�.

// class�� ���̺��� pascal-case -> snake-case�� ����
// Todo class -> todo table
// StudentInfo class -> student_info table

// �ʵ�� �÷��� camel-case -> snake-case�� ����
// createdTime field -> created_time column

// �ڵ� ���迡 ���� �����ͺ��̽� ������ ��������� ���
// auto-migration

@Data
@Entity
public class Todo {

	// @Id -> ���̺��� PK(����/��ǥ �÷�)
	@Id
	// @GeneratedValue -> �ʵ� �� ���� ��� ����, IDENTITY�� �����ͺ��̽��� �ڵ���������
	// ���
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String memo;
	private Long createdTime;
}
