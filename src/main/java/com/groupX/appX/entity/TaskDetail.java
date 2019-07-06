package com.groupX.appX.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "task_detail")
public class TaskDetail extends BaseEntity{
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "task_id")
	@JsonIgnore
	private Task task;
	
	@Column(name= "auto_reminder")
	private Boolean autoreminder;
	
	@Column(name= "custom_reminder")
	private Date customreminder;
	
	@Column(name= "priority")
	private String priority;
	
	@Column(name= "due_date")
	private Date dueDate;
	
	@Column(name = "task_content")
	private String taskContent;

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Boolean getAutoreminder() {
		return autoreminder;
	}

	public void setAutoreminder(Boolean autoreminder) {
		this.autoreminder = autoreminder;
	}

	public Date getCustomreminder() {
		return customreminder;
	}

	public void setCustomreminder(Date customreminder) {
		this.customreminder = customreminder;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getTaskContent() {
		return taskContent;
	}

	public void setTaskContent(String taskContent) {
		this.taskContent = taskContent;
	}
	
	
	
}
