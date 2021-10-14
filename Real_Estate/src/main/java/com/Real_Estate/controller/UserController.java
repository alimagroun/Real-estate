package com.Real_Estate.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.Real_Estate.model.User;
import com.Real_Estate.repository.UserRepository;

@RestController @CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class UserController {
	
    @Autowired
    private UserRepository userRepository;



    
    @PostMapping("/user")
    public User createUser(@Valid @RequestBody User user) {
    	return userRepository.save(user);
    }
    
    
    @GetMapping("/users")
    public List<User> getAllEmployees() {
        return userRepository.findAll();
    }
    
    
}
