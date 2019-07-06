package com.groupX.appX.service;

import java.util.Optional;

import com.groupX.appX.entity.Task;
import com.groupX.appX.entity.TaskDetail;
import com.groupX.appX.entity.User;

public interface TaskDetailService {

	Optional<TaskDetail> findById(Long taskDetailId);

	void deleteTaskDetail(TaskDetail taskDetail);

	void deleteById(Long taskDetailId);

	TaskDetail updateTaskDetail(TaskDetail taskDetail);

	User getUserOfTaskDetail(Long taskDetailId);
	
}
