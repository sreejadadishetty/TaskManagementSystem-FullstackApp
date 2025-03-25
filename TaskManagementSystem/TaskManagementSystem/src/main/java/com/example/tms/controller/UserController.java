package com.example.tms.controller;

import java.util.List;
import java.util.Optional;

import org.eclipse.angus.mail.iap.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.tms.dto.ContactDTO;
import com.example.tms.dto.TaskStatusRequest;
import com.example.tms.dto.UpdatePasswordRequest;
import com.example.tms.entity.Task;
import com.example.tms.entity.TaskStatus;
import com.example.tms.entity.User;
import com.example.tms.repository.UserRepository;
import com.example.tms.service.EmailService;
import com.example.tms.service.FileUploadService;
import com.example.tms.service.TaskService;
import com.example.tms.service.UserService;


@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
//@PreAuthorize("hasRole('USER')")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	FileUploadService fileUploadService;
	
	@GetMapping("/tasks")
	public ResponseEntity<List<Task>> getCurrentUserTasks(@RequestParam Long userId){
		User user=userService.getUserById(userId);
		return ResponseEntity.ok(taskService.getTaskByUser(user));
		
	}
	
	@PostMapping("/tasks/{userId}")
	public ResponseEntity<Task> createTask(@RequestBody Task task, @PathVariable  Long userId){
		User user=userService.getUserById(userId);
		task.setUser(user);
		return ResponseEntity.ok(taskService.createTask(task));
		
		
	}
	@PutMapping("/tasks/{taskId}")
	public ResponseEntity<Task> updateTask(@PathVariable Long taskId, @RequestBody TaskStatusRequest status){
		return ResponseEntity.ok(taskService.updateTaskstatus(taskId, status));
	}
	@PutMapping("/users/{userId}")
	public ResponseEntity<User> updateUser(@PathVariable Long userId,@RequestBody User user){
		return ResponseEntity.ok(userService.updateUser(userId,user));
	}
	@GetMapping("/login/user")
	public ResponseEntity<User> loginUser(@RequestParam String email, @RequestParam String password) throws Exception{
		return ResponseEntity.ok(userService.userLogin(email, password));
	}
	
	@GetMapping("/getUserData/{userId}")
	public ResponseEntity<Optional<User>> fetchUserDetails(@PathVariable Long userId){
		return ResponseEntity.ok(userService.getUserBasedOnId(userId));
	}
	
	@PutMapping("/update/password")
	public ResponseEntity<String> updatePassword(@RequestBody UpdatePasswordRequest request){
		
		return ResponseEntity.ok(userService.updatePassword(request.getId(), request.getPassword()));
	}
	@PostMapping("/contact/send")
	public String handleContactForm(@RequestBody ContactDTO form) {
		String subject="New Contact Form Submission from : "+ form.getName();
		 String body = "Name: " + form.getName() + "\n"
                 + "Email: " + form.getEmail() + "\n"
                 + "Message: " + form.getMessage();
		 emailService.sendSimpleMessage("pinkysreeja321@gmail.com", subject, body);
		 return "Message Sent Successfully";
         
	}
	@PostMapping("/upload")
	public ResponseEntity<String> uploadUsers(@RequestParam("file") MultipartFile file){
		if(file.isEmpty()) {
			return ResponseEntity.badRequest().body("File is Empty");
			
		}
		List<User> user=fileUploadService.parseCSVFile(file);
		System.out.println(user);
		userService.saveAllUsers(user);
		return ResponseEntity.ok("File uploaded and users saved Successfully");
	}
	

}
