package com.example.Springboot.controller;


import com.example.Springboot.Entity.UserDTO;
import com.example.Springboot.Repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerLogin {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;



    @Test
    @DatabaseSetup("Migration/test-user-data.sql")
    public void testLoginSuccess() throws Exception {

        UserDTO loginRequest = new UserDTO();
        loginRequest.setFullname("AhmedMagdy");
        loginRequest.setEmail("Ahmed@example.com");
        loginRequest.setPassword("Ahmed@2468");

        // Perform the login request
        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk()) // Expect HTTP 200 OK
                .andExpect(content().string("Login successful!")); //
    }

    @Test
    @DatabaseSetup("Migration/test-user-data.sql")
    public void testLoginFailure() throws Exception {
        // Create a login request with incorrect password
        UserDTO loginRequest = new UserDTO();
        loginRequest.setFullname("AhmedMagdy");
        loginRequest.setEmail("Ahmedclear@example.com");
        loginRequest.setPassword("hassan@342#3");

        // Perform the login request
        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().is4xxClientError()) // Expect a client error (400 series)
                .andExpect(content().string("Invalid credentials")); // Expect the error message
    }
}
