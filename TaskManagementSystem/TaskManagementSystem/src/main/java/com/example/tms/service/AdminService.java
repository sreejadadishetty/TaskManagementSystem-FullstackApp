package com.example.tms.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tms.entity.Role;
import com.example.tms.entity.User;
import com.example.tms.exception.ResourceNotFoundException;
import com.example.tms.repository.UserRepository;

@Service
public class AdminService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	EmailService emailService;
	
	public User registerAdmin(String name,String email,Role role,String password) {
       
		Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new RuntimeException("Admin with email " + email + " already exists.");
        }

      
        User admin = new User();
        admin.setName(name);
        admin.setEmail(email);
        admin.setPassword(password); 
        
        admin.setRole(Role.ADMIN);
        admin.setEnabled(true);

        User savedAdmin = userRepository.save(admin);
        
        
        
        return savedAdmin;
    }
	
	public boolean isAdmin(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("email", "", email));
        return "ADMIN".equals(user.getRole());
    }
	
	

    public String adminLogin(String email,String password) throws Exception {
       Optional<User> optionalUser = userRepository.findByEmail(email);
        
        if (optionalUser.isEmpty()) {
            return "Admin not found";
        }

        User user = optionalUser.get();

        
        if (user.getRole() != Role.ADMIN) {
            return "Access denied: Only admins can log in.";
        }

       
        if (!user.getPassword().equals(password)) {
           return "Invalid password";
        }

       
        return "Admin login successful!";
    }
	

}
