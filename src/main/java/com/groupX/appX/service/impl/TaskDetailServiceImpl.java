package com.groupX.appX.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupX.appX.entity.Task;
import com.groupX.appX.entity.TaskDetail;
import com.groupX.appX.entity.User;
import com.groupX.appX.repository.TaskDetailRepository;
import com.groupX.appX.service.TaskDetailService;

@Service
public class TaskDetailServiceImpl implements TaskDetailService {

	@Autowired
	private TaskDetailRepository taskDetailRepository;


	@Override
	public void deleteTaskDetail(TaskDetail taskDetail) {
		taskDetailRepository.delete(taskDetail);
	}

	@Override
	public void deleteById(Long id) {
		taskDetailRepository.deleteById(id);
	}

	@Override
	public Optional<TaskDetail> findById(Long taskId) {
		return taskDetailRepository.findById(taskId);
	}


	@Override
	public User getUserOfTaskDetail(Long taskDetailId) {
		
		Optional<TaskDetail> taskDetail = taskDetailRepository.findById(taskDetailId);
		if(taskDetail.isPresent()) {
			User user = taskDetail.get().getTask().getUser();
			return user;
		}
		return null;
	}

	@Override
	public TaskDetail updateTaskDetail(TaskDetail taskDetail) {
		return taskDetailRepository.save(taskDetail);
	}




}
