package com.groupX.appX.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groupX.appX.entity.User;
import com.groupX.appX.exception.UserNotFoundException;
import com.groupX.appX.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		return userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}

	@Transactional
	public User loadUserById(Long id) {

		return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not with " + id + "found" ));
	}
}
