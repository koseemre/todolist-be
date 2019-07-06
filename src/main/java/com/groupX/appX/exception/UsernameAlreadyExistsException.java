package com.groupX.appX.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UsernameAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = -2284003000404032030L;

	public UsernameAlreadyExistsException(String message) {
        super(message);
    }
}
