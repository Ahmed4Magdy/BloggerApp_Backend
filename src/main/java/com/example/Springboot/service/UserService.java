package com.example.Springboot.service;

import com.example.Springboot.Entity.UserDTO;
import com.example.Springboot.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String signup(UserDTO userDTO) throws Exception {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
           throw new RuntimeException("User already exists!");
   }

        UserDTO user = new UserDTO();
        user.setFullname(userDTO.getFullname());
        user.setEmail(userDTO.getEmail());
        user.setPassword(encodePassword(userDTO.getPassword()));
        userRepository.save(user);
        return "User signed up successfully!";
    }


    private String encodePassword(String password) {
        return password;
    }
    public String login(UserDTO userDTO) {
        UserDTO existingUser = userRepository.findByEmail(userDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (userDTO.getPassword().equals(existingUser.getPassword())) {
            return "Login successful!";
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }


}


