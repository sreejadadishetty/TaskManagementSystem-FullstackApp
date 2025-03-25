package com.example.tms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tms.entity.Task;
import com.example.tms.entity.User;
import com.example.tms.repository.UserRepository;
import com.example.tms.service.AdminService;
import com.example.tms.service.TaskService;
import com.example.tms.service.UserService;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*")
//@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
	
	@Autowired
	UserService userService;
	
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	AdminService adminService;
	
	@GetMapping("/tasks")
	public ResponseEntity<List<Task>> getAllTasks(){
		return ResponseEntity.ok(taskService.getAllTasks());
	}
	
	@DeleteMapping("/task/{taskId}")
	public ResponseEntity<String> deleteTask(@PathVariable Long taskId){
		taskService.deleteTask(taskId);
		return ResponseEntity.ok("Task deleted SuccessFully");
		
		
	}
	
	@GetMapping("/search/users")
	public ResponseEntity<List<User>> searchByNameOrEmailId(@RequestParam String name, @RequestParam String email){
		return ResponseEntity.ok(userService.searchByNameOrEmailId(name, email));
	}
	
	@GetMapping("/viewdetails")
	public ResponseEntity<List<User>> getAllUsers(){
		return  ResponseEntity.ok(userService.getAllUsers());
	}
	
	@PostMapping("/adduser")
	public ResponseEntity<User> createUser(@RequestBody User user){
		return ResponseEntity.ok(userService.createUser(user.getName(), user.getEmail(), user.getRole(), user.getPassword()));
		
	}
	
	@DeleteMapping("/deleteUser/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable Long userId){
		userService.deleteUser(userId);
		return ResponseEntity.ok("User Deleted Successfully");
	}
	
	@PostMapping("/admin/login")
	public ResponseEntity<String> loginAdmin(@RequestBody User user) throws Exception{
		return ResponseEntity.ok(adminService.adminLogin(user.getEmail(), user.getPassword()));
	}
	
	@PostMapping("/admin/register")
    public ResponseEntity<User> registerAdmin(@RequestBody User user) {
        User admin = adminService.registerAdmin(user.getName(), user.getEmail(), null, user.getPassword());
        return ResponseEntity.ok(admin);
    }
     
	

}
