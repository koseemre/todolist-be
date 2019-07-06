package com.groupX.appX.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.groupX.appX.entity.User;
import com.groupX.appX.repository.UserRepository;
import com.groupX.appX.service.impl.UserServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	UserServiceImpl userService;

	@Test
	public void test_total_users_by_mockito() {

		List<User> users = new ArrayList<User>();
		users.add(new User("emre@gmail.com", "pass", "pass", "emre", "kose", 24L, "pro", null));

		when(userService.getUsers()).thenReturn(users);
		int size = userService.getUsers().size();
		System.out.println(size);
		assertEquals(size, 2);

	}

	/*
	 * @Test public void test_save_user_should_return_user() { User user = new
	 * User("testName", "testSurName", 24L, "testType");
	 * when(userRepository.save(any(User.class))).thenReturn(user); User created =
	 * userService.addUser(user);
	 * assertThat(created.getName()).isSameAs(user.getName()); }
	 */

	@Test(expected = NullPointerException.class)
	public void test_save_user_without_surname_should_throw_exception() {

		User user = new User("emre@gmail.com", "pass", "pass", "emre", null, 24L, "pro", null);
		userService.addUser(user);
	}

}