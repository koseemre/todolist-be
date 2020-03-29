package com.groupX.appX.validator;

import com.groupX.appX.payload.LoginRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class LoginRequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return LoginRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        LoginRequest request = (LoginRequest) target;

        if(request.getPassword().isEmpty()){
            errors.rejectValue("password","Length", "password should be entered");
        }

        if(request.getUsername().isEmpty()){
            errors.rejectValue("userName","Length", "username should be entered");
        }
    }
}
