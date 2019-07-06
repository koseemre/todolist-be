package com.groupX.appX.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.groupX.appX.entity.Task;


public interface TaskRepository extends JpaRepository<Task, Long> {
}
