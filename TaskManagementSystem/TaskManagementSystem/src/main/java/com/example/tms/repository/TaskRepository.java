package com.example.tms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tms.entity.Task;
import com.example.tms.entity.User;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{
	List<Task> findByUser(User user);
	List<Task> findByUserOrderByCreatedAtDesc(User user);
    List<Task> findAllByOrderByCreatedAtDesc();

}
