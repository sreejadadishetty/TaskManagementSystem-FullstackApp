package com.example.tms.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tms.dto.TaskStatusRequest;
import com.example.tms.entity.Task;
import com.example.tms.entity.TaskStatus;
import com.example.tms.entity.User;
import com.example.tms.exception.ResourceNotFoundException;
import com.example.tms.repository.TaskRepository;
import com.example.tms.repository.UserRepository;

@Service
public class TaskService {
	
	@Autowired
	TaskRepository taskRepository;
	
	@Autowired
	UserRepository userRepository;
	
	public Task createTask(Task task) {
		task.setCreatedDate(LocalDateTime.now());
		return taskRepository.save(task);
	}
	
	public Task updateTaskstatus(Long taskId, TaskStatusRequest status) {
		Task task=taskRepository.findById(taskId).orElseThrow(()-> new ResourceNotFoundException("Task", "Id", taskId));
		System.out.println("statuss........="+status);
		TaskStatus status1st = status.getStatus();
		task.setStatus(status1st);
		return taskRepository.save(task);
	}
	
	public List<Task> getTaskByUser(User user){
		return taskRepository.findByUserOrderByCreatedAtDesc(user);
	}
	
	public List<Task> getAllTasks(){
		return taskRepository.findAllByOrderByCreatedAtDesc();
	}
	
	public String deleteTask(Long taskId) {
		Task task=taskRepository.findById(taskId).orElseThrow(()->new ResourceNotFoundException("task", "id", taskId));
		taskRepository.delete(task);
		return "task deleted successfully";
	}

}
