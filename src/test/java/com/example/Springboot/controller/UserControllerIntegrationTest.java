package com.example.Springboot.controller;

import com.example.Springboot.Entity.User;
import com.example.Springboot.Repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;  // To convert Java objects to JSON

    private User testUser;

    @BeforeEach
    public void setup() {
        // Initialize a test user before each test
        testUser = new User();
        testUser.setFullname("ahmed");
        testUser.setEmail("ooo@gmail.com");
        testUser.setPassword("h44");
    }

    @Test
    @Commit
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
    public void testSignupWithExistingEmail() throws Exception {
        // Save the test user to simulate an existing user
//        userRepository.save(testUser);

        // Try to sign up with the same email
        String userJson = objectMapper.writeValueAsString(testUser);

        mockMvc.perform(post("/api/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isConflict()) //
                .andExpect(content().string("User already exists!"));
    }
}


