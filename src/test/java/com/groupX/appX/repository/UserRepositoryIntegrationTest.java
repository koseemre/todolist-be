package com.groupX.appX.repository;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.groupX.appX.entity.User;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryIntegrationTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private UserRepository userRepository;

	@Test
	public void whenFindByName_thenReturnUser() {

		int initialSize = userRepository.findAll().size();
		User userX = new User("emre@gmail.com", "pass", "pass", "emre", "kose", 24L, "pro", null);
		entityManager.persist(userX);

		// when
		Optional<User> found = userRepository.findByName(userX.getName());
		// then
		assertNotNull(found.get());
		// or then
		assertEquals(found.get().getName(), userX.getName());

		User user = entityManager.find(User.class, found.get().getId());
		entityManager.remove(user);

		found = userRepository.findByName(userX.getName());
		assertFalse(found.isPresent());

		int endSize = userRepository.findAll().size();
		assertEquals(initialSize, endSize);
	}

}
