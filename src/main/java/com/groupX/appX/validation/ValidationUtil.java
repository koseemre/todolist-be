package com.groupX.appX.validation;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.groupX.appX.entity.User;

@Component
public class ValidationUtil {

	public void validateUser(User user) {

		Assert.notNull(user.getName(), "User name must not be null");
		Assert.notNull(user.getSurname(), "User surname must not be null");
		Assert.notNull(user.getUserType(), "User type must not be null");

	}
}
