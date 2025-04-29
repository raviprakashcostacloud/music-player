package com.example.userService.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.userService.Entity.User;


public interface UserRepository extends MongoRepository<User, String> {
	Optional<User> findByUsername(String username);

}
