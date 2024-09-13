package com.example.Springboot.service;

import com.example.Springboot.Entity.User;
import com.example.Springboot.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public String signup(User user) throws Exception {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists!");
        }
        user.setPassword(encodePassword(user.getPassword()));  // Add password encryption
        userRepository.save(user);
        return "User signed up successfully!";
    }


    private String encodePassword(String password) {
        return password;
    }


}

//    public String login(User user) {
//        User existingUser = userRepository.findByEmail(user.getEmail())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        if (user.getPassword().equals(existingUser.getPassword())) {
//            // Return success message or token if needed
//            return "Login successful!";
//        } else {
//            throw new RuntimeException("Invalid credentials");
//        }
//    }
