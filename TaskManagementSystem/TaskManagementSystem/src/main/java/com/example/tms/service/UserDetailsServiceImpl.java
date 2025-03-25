//package com.example.tms.service;
//
//import java.util.Collection;
//import java.util.Collections;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import com.example.tms.entity.Role;
//import com.example.tms.entity.User;
//import com.example.tms.repository.UserRepository;
//
//public class UserDetailsServiceImpl implements UserDetailsService{
//	
//	@Autowired
//	UserRepository userRepository;
//
//	@Override
//	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//		User user= userRepository.findbyEmail(email);
//				
//		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
//
//				user.isEnabled(), true, true, true, getAuthorities(user.getRole()));
//	}
//
//	private Collection<? extends GrantedAuthority> getAuthorities(Role role) {
//		
//		return Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+ role.name()));
//	}
//
//}
