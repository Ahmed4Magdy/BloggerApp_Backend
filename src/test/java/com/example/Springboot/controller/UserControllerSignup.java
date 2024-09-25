package com.example.Springboot.controller;

import com.example.Springboot.Entity.UserDTO;
import com.example.Springboot.Repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerSignup {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;  // To convert Java objects to JSON

    private UserDTO testUser;

    @BeforeEach
    public void setup() {
        // Initialize a test user before each test
        testUser = new UserDTO();
        testUser.setFullname("Ahmed@gmail.com");
        testUser.setEmail("Ahmed@gmail.com");
        testUser.setPassword("ahmed@23456##");
    }

    @Test
    @Rollback(false)
    public void testSignupSuccess() throws Exception {
        // Convert the user object to JSON
        String userJson = objectMapper.writeValueAsString(testUser);

        mockMvc.perform(post("/api/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(content().string("User signed up successfully!"));
    }

    @Test
    @Rollback(false)
    public void testSignupWithExistingEmail() throws Exception {

        UserDTO existingUser = new UserDTO();
        existingUser.setFullname(testUser.getFullname());
        existingUser.setEmail(testUser.getEmail());
        existingUser.setPassword(testUser.getPassword()); // Assume password is encoded
        userRepository.save(existingUser);
        String userJson = objectMapper.writeValueAsString(testUser);

        mockMvc.perform(post("/api/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isForbidden()) //
                .andExpect(content().string("User already exists!"));
    }
}


