package com.groupX.appX.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.groupX.appX.entity.TaskDetail;


public interface TaskDetailRepository extends JpaRepository<TaskDetail, Long> {
}
