package com.magroun.realestate.services;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.magroun.realestate.model.User;
import com.magroun.realestate.repository.UserRepository;

import java.time.LocalDateTime;
@Service
public class UserService {
    private UserRepository userRepository;

    // Constructor
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void generateResetCodeAndSaveUser(User user) {
        // Generate a random 6-digit number
    	Integer resetCode = generateRandomSixDigitNumber();

        // Set the generated number to the user's resetCode field
        System.out.println("Reset Code: " + resetCode);
        user.setResetCode(resetCode);

        // Set the current time to the user's resetCodeTime field
        LocalDateTime currentTime = LocalDateTime.now();
        user.setResetCodeTime(currentTime);

        // Save the user using the UserRepository
        userRepository.save(user);
    }

    private Integer generateRandomSixDigitNumber() {
        // Generate a random 6-digit number
        Random random = new Random();
        return random.nextInt(900000) + 100000; // Generates a number between 100,000 and 999,999
    }
}

