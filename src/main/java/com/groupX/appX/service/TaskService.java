package com.groupX.appX.service;

import java.util.List;
import java.util.Optional;

import com.groupX.appX.entity.Task;
import com.groupX.appX.entity.TaskDetail;

public interface TaskService {

	Optional<Task> findById(Long taskId, String username);

	Task addTask(Task task, String username);

	void deleteTask(Task task, String username);

	void deleteById(Long taskId, String username);

	List<Task> findTasksByUsername(String username,String usernameEx);
	
	List<TaskDetail> getTaskDetailsByUserId(Long userId, String username);
	
	List<Task> findTasksByUserId(Long userId, String username);
	
	
}
