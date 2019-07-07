package com.groupX.appX.controller;

import static com.groupX.appX.security.SecurityConstants.TOKEN_PREFIX;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.groupX.appX.entity.User;
import com.groupX.appX.enums.ErrorCode;
import com.groupX.appX.exception.UserNotFoundException;
import com.groupX.appX.payload.JWTLoginSucessReponse;
import com.groupX.appX.payload.LoginRequest;
import com.groupX.appX.security.JwtTokenProvider;
import com.groupX.appX.service.UserService;
import com.groupX.appX.service.impl.MapValidationErrorService;
import com.groupX.appX.validator.UserValidator;

@RestController
@RequestMapping("/api/users")
public class UserController {

	Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private MapValidationErrorService mapValidationErrorService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {
		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if (errorMap != null)
			return errorMap;

		/*
		 * hangi user service(customUserDetailsService) i ve hangi şifreleme
		 * yöntemini(bCryptPasswordEncoder) kullanığımızı belirttiğimiz için
		 * (SecurityConfig in configure(AuthenticationManagerBuilder
		 * authenticationManagerBuilder) methodu içinde), password kontrolünü kendisi
		 * yapar ve hata durumunda gerekli exception ı fırlatabilir
		 */
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = TOKEN_PREFIX + tokenProvider.generateToken(authentication);
		
		logger.info("login returning");
		return ResponseEntity.ok(new JWTLoginSucessReponse(true, jwt));
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {
		// Validate passwords match
		// hata varsa result(Errors errors) içine ekle
		userValidator.validate(user, result);
		// hataları map içine atar
		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		// map boş değilse hata map ine dön
		if (errorMap != null)
			return errorMap;

		// exception atılırsa CustomResponseEntityExceptionHandler(@ControllerAdvice)
		// tarafından yakalanır ve ResponseEntity döndürülür
		User newUser = userService.saveUser(user);

		return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
	}

	@GetMapping("/getUsers")
	public @ResponseBody List<User> getUsers() {
		return userService.getUsers();
	}

	@GetMapping("/users/{id}")
	public User retrieveUser(@RequestParam Long userId) throws UserNotFoundException {

		return userService.findById(userId)
				.orElseThrow(() -> new UserNotFoundException(userId, ErrorCode.USER_NOT_FOUND));

	}

	@DeleteMapping("/users/{id}")
	public void deleteUser(@RequestParam Long id) {
		userService.deleteById(id);
	}

	@PutMapping("/users/{id}")
	public void updateUser(@Valid @RequestBody User userDetails) {

		User user = userService.findById(userDetails.getId())
				.orElseThrow(() -> new UserNotFoundException(userDetails.getId(), ErrorCode.USER_NOT_FOUND));
		userService.addUser(userDetails);

	}

}
