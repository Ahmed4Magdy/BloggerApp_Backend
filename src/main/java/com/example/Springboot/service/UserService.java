package com.example.Springboot.service;


import com.example.Springboot.Entity.User;
import com.example.Springboot.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String signup(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists!");
        }
        user.setPassword(encodePassword(user.getPassword()));  // Add password encryption
        userRepository.save(user);
        return "User signed up successfully!";
    }

    private String encodePassword(String password) {
        // Implement password encoding logic (e.g., BCryptPasswordEncoder)
        return password; // Placeholder
    }
}
