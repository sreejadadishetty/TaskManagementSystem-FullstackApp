package com.example.tms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.tms.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
//	@Query("SELECT u FROM User u WHERE u.email = :email")
//	User findUserByEmailAddress(String email);
	
	List<User> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email);

	Optional<User> findByEmail(String email);
	
	User findByEmailAndPassword(String email,String password);

}
