package com.groupX.appX.exception;

import com.groupX.appX.entity.Task;
import com.groupX.appX.enums.ErrorCode;

public class TaskDetailNotFoundException extends Exception {

	private static final long serialVersionUID = 5901211664239452423L;
	private final ErrorCode code;

	public TaskDetailNotFoundException(Long taskDetailId, ErrorCode code) {
		super("TaskDetail with id:" + taskDetailId + " not found");
		this.code = code;
	}
	public TaskDetailNotFoundException(Task task, ErrorCode code) {
		super("TaskDetail with task id:" + task.getId() + " not found");
		this.code = code;
	}
	public TaskDetailNotFoundException(Long taskDetailId, Throwable cause, ErrorCode code) {
		super("TaskDetail with id:" + taskDetailId + " not found", cause);
		this.code = code;
	}

	public TaskDetailNotFoundException(ErrorCode code) {
		super();
		this.code = code;
	}

	public TaskDetailNotFoundException(Throwable cause, ErrorCode code) {
		super(cause);
		this.code = code;
	}

	public ErrorCode getCode() {
		return this.code;
	}
}
