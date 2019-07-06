package com.groupX.appX.service;

import java.util.List;
import java.util.Optional;

import com.groupX.appX.entity.Task;
import com.groupX.appX.entity.User;

public interface UserService {

	Optional<User> findById(Long id);

	List<User> getUsers();

	User addUser(User user);

	void deleteUser(User user);

	void deleteById(Long id);
	
	User updateUser(User user);
	
	List<Task> getTasksOfUser(Long userId);
	
	User saveUser (User newUser);
	 
}
