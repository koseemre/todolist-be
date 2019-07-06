package com.groupX.appX.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.groupX.appX.entity.Task;
import com.groupX.appX.enums.ErrorCode;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 5901211664239452423L;
	private ErrorCode code;

	public UserNotFoundException(String message) {
		super(message);
	}
	
	public UserNotFoundException(Long userId, ErrorCode code) {
		super("User with id:" + userId + " not found");
		this.code = code;
	}
	
	public UserNotFoundException(Task task, ErrorCode code) {
		super("User with that task id:" + task.getId() + " not found");
		this.code = code;
	}

	public UserNotFoundException(Long userId, Throwable cause, ErrorCode code) {
		super("User with id:" + userId + " not found", cause);
		this.code = code;
	}

	public UserNotFoundException(ErrorCode code) {
		super();
		this.code = code;
	}

	public UserNotFoundException(Throwable cause, ErrorCode code) {
		super(cause);
		this.code = code;
	}

	public ErrorCode getCode() {
		return this.code;
	}
}
