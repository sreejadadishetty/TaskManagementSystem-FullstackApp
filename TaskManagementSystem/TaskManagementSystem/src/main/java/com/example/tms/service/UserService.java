package com.example.tms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.example.tms.entity.Role;
import com.example.tms.entity.User;
import com.example.tms.exception.ResourceNotFoundException;
import com.example.tms.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	EmailService emailService;
	
	public User createUser(String name,String email,Role role,String password) {
		User user=new User();
		user.setEmail(email);
		user.setPassword(password);
		user.setEnabled(true);
		user.setName(name);
		user.setRole(role);
		
		User savedUser= userRepository.save(user);
		sendWelcomeMail(user);
		return savedUser;
	}
	
	private void sendWelcomeMail(User user) {
		String subject="Your task management account is ready";
		String body=String.format("Dear %s,\n\n" +
	            "Your account has been created by the administrator.\n\n" +
	            "Login Credentials:\n" +
	            "Email: %s\n" +
	            "Password: %s\n\n" +
	            "You can reset your passwordby using the following link: \n" +
	            "<a href=\'http://localhost:3000/reset/password/%s\'>Click here to reset</a>\n\n" +
	            "Best regards,\n" +
	            "Task Management Team",
	            user.getName(),
	            user.getEmail(),
	            user.getPassword(),
	            user.getId()
	        );
		 body = body.replace("'", "");
		emailService.sendSimpleMessage(user.getEmail(), subject, body);
		
	}

	public String deleteUser(Long userId) {
		userRepository.deleteById(userId);
		return "user deleted successfully";
	}
	
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	
	public Optional<User> getUserBasedOnId(Long userId) {
		return userRepository.findById(userId);
	}
	
	
	public User updateUser(Long userId, User updatedUser) {
		User user=userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user", "id", userId));
		user.setEmail(updatedUser.getEmail());
		user.setName(updatedUser.getName());
		user.setEnabled(true);
		user.setRole(Role.USER);
		user.setPassword(updatedUser.getPassword());
		return userRepository.save(user);
	}
	public String updatePassword(Long userId, String updatedPassword) throws ResourceNotFoundException {
		User user=userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("email", "id", userId));
		user.setPassword(updatedPassword);
		userRepository.save(user);
		return " password updated Successfully!!";
		
		
	}
	
	public List<User> searchByNameOrEmailId(String name, String email){
		return userRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(name, email);
	}
	
	public User getUserById(Long id) {
		return userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("user", "id", id));
	}
	
	public User userLogin(String email,String password) throws Exception {
		User user= userRepository.findByEmailAndPassword(email, password);
		if (user == null) {
            throw new Exception("Invalid email or password");
        }
        return user;
	}
	
	public void saveAllUsers(List<User> users) {
		userRepository.saveAll(users);
	}
	
	

}
