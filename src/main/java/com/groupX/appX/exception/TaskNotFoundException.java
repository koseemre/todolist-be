package com.groupX.appX.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.groupX.appX.enums.ErrorCode;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TaskNotFoundException extends RuntimeException {

	private ErrorCode code;

	public TaskNotFoundException(String message) {
		super(message);
	}

	public TaskNotFoundException(Long taskId, ErrorCode code) {
		super("Task with id:" + taskId + " not found");
		this.code = code;
	}

	public TaskNotFoundException(Long taskId, Throwable cause, ErrorCode code) {
		super("task with id:" + taskId + " not found", cause);
		this.code = code;
	}

	public TaskNotFoundException(ErrorCode code) {
		super();
		this.code = code;
	}

	public TaskNotFoundException(Throwable cause, ErrorCode code) {
		super(cause);
		this.code = code;
	}

	public ErrorCode getCode() {
		return this.code;
	}
}
