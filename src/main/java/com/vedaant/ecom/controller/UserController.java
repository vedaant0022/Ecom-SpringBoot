package com.vedaant.ecom.controller;

import com.vedaant.ecom.model.User;
import com.vedaant.ecom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class UserController {
    @Autowired
    UserService service;

    @PostMapping("/user")
    public ResponseEntity<?> addUser(@RequestBody User user){

        if(user.getEmail() == null || user.getPassword() == null){
            return new ResponseEntity<>("Enter All Fields",HttpStatus.NOT_FOUND);
        }
        User check = service.checkUser(user.getEmail());
        if(check != null){
            return new ResponseEntity<>("User exists", HttpStatus.NOT_FOUND);
        }
        try{
            User u1 = service.addUser(user);
            return new ResponseEntity<>(u1,HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @PostMapping("/user/login")
    public ResponseEntity<?> loginUser(@RequestParam String email,@RequestParam String password) {

        User user = service.checkUser(email);

        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("User does not exist");
        }
        if (!encoder.matches(password, user.getPassword())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid Credentials");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Login Success");
        response.put("user", user);

        return ResponseEntity.ok(response);
    }





}
