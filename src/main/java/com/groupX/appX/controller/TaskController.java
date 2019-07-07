package com.groupX.appX.controller;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.groupX.appX.entity.Task;
import com.groupX.appX.entity.TaskDetail;
import com.groupX.appX.entity.User;
import com.groupX.appX.enums.ErrorCode;
import com.groupX.appX.exception.TaskDetailNotFoundException;
import com.groupX.appX.exception.TaskNotFoundException;
import com.groupX.appX.exception.UserNotFoundException;
import com.groupX.appX.service.TaskDetailService;
import com.groupX.appX.service.TaskService;
import com.groupX.appX.service.UserService;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin
public class TaskController {

	Logger logger = LoggerFactory.getLogger(TaskController.class);

	@Autowired
	private TaskService taskService;

	@Autowired
	private TaskDetailService taskDetailService;

	@Autowired
	private UserService userService;

	@GetMapping("/getTasks/{userId}")
	public List<Task> getTaskById(@RequestParam Long userId, Principal principal) {
		
		logger.info("task controller principal name:" + principal.getName());
		return taskService.findTasksByUserId(userId, principal.getName());
	}

	@GetMapping("/getTasksByUsername/{username}")
	public List<Task> getTaskByUsername(@RequestParam String username, Principal principal) {
		return taskService.findTasksByUsername(username, principal.getName());
	}

	@PostMapping("/addTask/{userId}")
	public Task addTask(@RequestParam Long userId, @RequestBody Task task, Principal principal)
			throws UserNotFoundException {

		logger.debug("principal name:" + principal.getName());

		User user = userService.findById(userId)
				.orElseThrow(() -> new UserNotFoundException(task, ErrorCode.TASK_NOT_FOUND));

		task.setUser(user);
		TaskDetail taskDetail = task.getTaskDetail();
		taskDetail.setTask(task);
		return taskService.addTask(task, principal.getName());

	}

	@GetMapping("/tasks/{id}")
	public Task retrieveTask(@RequestParam Long taskId, Principal principal) throws TaskNotFoundException {

		return taskService.findById(taskId, principal.getName())
				.orElseThrow(() -> new TaskNotFoundException(taskId, ErrorCode.TASK_NOT_FOUND));

	}

	@DeleteMapping("/deleteTask/{id}")
	public void deleteTask(@RequestParam Long taskId, Principal principal) {
		taskService.deleteById(taskId, principal.getName());
	}

	@PutMapping(path = "/updateTask/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void updateTask(@RequestBody TaskDetail taskDetail, @RequestParam Long taskId, Principal principal)
			throws TaskNotFoundException, TaskDetailNotFoundException {

		Task oldTask = taskService.findById(taskId, principal.getName())
				.orElseThrow(() -> new TaskNotFoundException(taskId, ErrorCode.TASK_NOT_FOUND));

		taskDetail.setId(oldTask.getTaskDetail().getId());
		taskDetail.setTask(oldTask);
		taskDetail.setCreateDate(oldTask.getTaskDetail().getCreateDate());
		taskDetail.setUpdateDate(new Date());
		taskDetailService.updateTaskDetail(taskDetail);

	}

}
