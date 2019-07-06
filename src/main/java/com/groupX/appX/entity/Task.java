package com.groupX.appX.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "task")
public class Task extends BaseEntity {

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User user;

	@OneToOne(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private TaskDetail taskDetail;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public TaskDetail getTaskDetail() {
		return taskDetail;
	}

	public void setTaskDetail(TaskDetail taskDetail) {
		this.taskDetail = taskDetail;
	}

}
