package com.groupX.appX.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.groupX.appX.entity.Task;
import com.groupX.appX.entity.User;
import com.groupX.appX.exception.UsernameAlreadyExistsException;
import com.groupX.appX.repository.UserRepository;
import com.groupX.appX.service.UserService;
import com.groupX.appX.validation.ValidationUtil;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ValidationUtil validationUtil;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser (User newUser){
        try{
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            //Username has to be unique (exception)
            newUser.setUsername(newUser.getUsername());
            // Make sure that password and confirmPassword match
            // We don't persist or show the confirmPassword
            newUser.setConfirmPassword("");
            return userRepository.save(newUser);

        }catch (Exception e){
            throw new UsernameAlreadyExistsException("Username '"+newUser.getUsername()+"' already exists");
        }
    }
    
	@Override
	public List<User> getUsers() {
		return userRepository.findAll();
	}

	@Override
	public User addUser(User user) {
		validationUtil.validateUser(user);
		return userRepository.save(user);
	}

	@Override
	public void deleteUser(User user) {
		userRepository.delete(user);
	}

	@Override
	public void deleteById(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public Optional<User> findById(Long id) {

		return userRepository.findById(id);
	}

	@Override
	public User updateUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public List<Task> getTasksOfUser(Long userId) {

		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent()) {
			return user.get().getTasks();
		}
		return null;
	}

}
