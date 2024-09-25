package com.example.Springboot.controller;

import com.example.Springboot.Entity.UserDTO;
import com.example.Springboot.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")

public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody UserDTO userDTo) {

        try {
            String response = userService.signup(userDTo);
            return ResponseEntity.ok("{\"message\":\"" + response + "\"}"); // Wrap the response in a JSON format

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{\"message\":\"" + e.getMessage() + "\"}");

        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserDTO userDTO) {
        try {
            String response = userService.login(userDTO);
            return ResponseEntity.ok("{\"message\":\"" + response + "\"}"); // Wrap the response in a JSON format

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{\"message\":\"" + e.getMessage() + "\"}"); //403
        }


    }
}
