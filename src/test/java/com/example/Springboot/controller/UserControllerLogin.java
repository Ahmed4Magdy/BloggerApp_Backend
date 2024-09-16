package com.example.Springboot.controller;


import com.example.Springboot.Entity.User;
import com.example.Springboot.Repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerLogin {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private User existingUser;

    @BeforeEach
    public void setup() {
        userRepository.deleteAll(); // Clean up the repository before each test
        existingUser = new User();
        existingUser.setEmail("Ahmed@example.com");
        existingUser.setPassword("a22");
        userRepository.save(existingUser);
    }

    @Test
    public void testLoginSuccess() throws Exception {

        User loginRequest = new User();
        loginRequest.setEmail("Ahmed@example.com");
        loginRequest.setPassword("a22");

        // Perform the login request
        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk()) // Expect HTTP 200 OK
                .andExpect(content().string("Login successful!")); //
    }

    @Test
    public void testLoginFailure() throws Exception {
        // Create a login request with incorrect password
        User loginRequest = new User();
        loginRequest.setEmail("Ahmed@example.com");
        loginRequest.setPassword("a202");

        // Perform the login request
        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().is4xxClientError()) // Expect a client error (400 series)
                .andExpect(content().string("Invalid credentials")); // Expect the error message
    }
}
