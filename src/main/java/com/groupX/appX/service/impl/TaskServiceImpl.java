package com.groupX.appX.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupX.appX.entity.Task;
import com.groupX.appX.entity.TaskDetail;
import com.groupX.appX.entity.User;
import com.groupX.appX.exception.UserNotFoundException;
import com.groupX.appX.repository.TaskRepository;
import com.groupX.appX.repository.UserRepository;
import com.groupX.appX.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {
	
	
	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public Task addTask(Task task, String username) {

		if (task.getUser().getUsername().equals(username)) {
			return taskRepository.save(task);
		} else {
			throw new UserNotFoundException(
					"User could not be found with id: " + Long.toString(task.getUser().getId()));
		}

	}

	@Override
	public void deleteTask(Task task, String username) {

		if (task.getUser().getUsername().equals(username)) {
			taskRepository.delete(task);
		}
	}

	@Override
	public void deleteById(Long taskId, String username) {

		Optional<Task> task = taskRepository.findById(taskId);
		if (task.isPresent() && task.get().getUser().getUsername().equals(username)) {
			taskRepository.deleteById(taskId);
		}
	}

	@Override
	public Optional<Task> findById(Long taskId, String username) {

		Optional<Task> task = taskRepository.findById(taskId);
		if (task.isPresent() && task.get().getUser().getUsername().equals(username)) {
			return task;
		} else {
			return null;
			// throw new TaskNotFoundException("Task could not be found with id: " +
			// Long.toString(taskId));
		}

	}

	@Override
	public List<TaskDetail> getTaskDetailsByUserId(Long userId, String username) {

		User user = userRepository.findById(userId).orElseThrow(
				() -> new UserNotFoundException("User could not be found with id: " + Long.toString(userId)));
		List<TaskDetail> taskDetails = null;
		List<Task> tasks = user.getTasks();
		if (username.equals(user.getUsername())) {
			if (tasks.size() > 0) {
				taskDetails = tasks.stream().sorted((o1, o2) -> o2.getCreateDate().compareTo(o1.getCreateDate()))
						.map(Task::getTaskDetail).collect(Collectors.toList());
			}
			return taskDetails;
		} else {
			throw new UserNotFoundException("User could not be found with id: " + Long.toString(userId));
		}
	}

	@Override
	public List<Task> findTasksByUserId(Long userId, String username) {
		
		System.out.println("LOGGGGGGGGGG:" + username);
		User user = userRepository.findById(userId).orElseThrow(
				() -> new UserNotFoundException("User could not be found with id: " + Long.toString(userId)));
		
		List<Task> tasks = user.getTasks();
		if (username.equals(user.getUsername())) {
			if (tasks.size() > 0) {
				tasks = tasks.stream()
						.sorted((o1, o2) -> o1.getTaskDetail().getDueDate().compareTo(o2.getTaskDetail().getDueDate()))
						.collect(Collectors.toList());
				return tasks;
			}
			return null;
		} else {
			throw new UserNotFoundException("User could not be found with id: " + Long.toString(userId));
		}

	}

	@Override
	public List<Task> findTasksByUsername(String username, String usernameEx) {

		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException("User could not be found with id: " + usernameEx));

		if (username.equals(usernameEx)) {
			return user.getTasks();
		}
		return null;
	}

	/*
	 * @Override public User getUserOfTask(Long taskId) {
	 * 
	 * Optional<Task> task = taskRepository.findById(taskId); if (task.isPresent())
	 * { User user = task.get().getUser(); return user; } return null; }
	 */

}
