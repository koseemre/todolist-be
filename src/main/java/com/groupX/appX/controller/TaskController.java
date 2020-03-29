package com.groupX.appX.controller;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
	public Task addTask(@RequestParam Long userId, @RequestBody Task task, Principal principal) {

		logger.debug("principal name:" + principal.getName());

		Optional<User> user = userService.findById(userId);
		if (user.isPresent()) {
			task.setUser(user.get());
			TaskDetail taskDetail = task.getTaskDetail();
			taskDetail.setTask(task);
			return taskService.addTask(task, principal.getName());
		} else
			return null;
	}

	@GetMapping("/tasks/{id}")
	public Task retrieveTask(@RequestParam Long taskId, Principal principal) {

		Optional<Task> task = taskService.findById(taskId, principal.getName());
		if(task.isPresent())
			return task.get();
		else
			return null;
	}

	@DeleteMapping("/deleteTask/{id}")
	public void deleteTask(@RequestParam Long taskId, Principal principal) {
		taskService.deleteById(taskId, principal.getName());
	}

	@PutMapping(path = "/updateTask/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void updateTask(@RequestBody TaskDetail taskDetail, @RequestParam Long taskId, Principal principal)
			 { // add try catch to throw specific exception like TaskNotFoundException, TaskDetailNotFoundException

		Optional<Task> oldTask = taskService.findById(taskId, principal.getName());
				//.orElseThrow(() -> new TaskNotFoundException(taskId, ErrorCode.TASK_NOT_FOUND));
		if(oldTask.isPresent()) {
			taskDetail.setId(oldTask.get().getTaskDetail().getId());
			taskDetail.setTask(oldTask.get());
			taskDetail.setCreateDate(oldTask.get().getTaskDetail().getCreateDate());
			taskDetail.setUpdateDate(new Date());
			taskDetailService.updateTaskDetail(taskDetail);
		}
	}

}
