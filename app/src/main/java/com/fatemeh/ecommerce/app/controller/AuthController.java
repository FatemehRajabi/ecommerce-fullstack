package com.fatemeh.ecommerce.app.controller;

import com.fatemeh.ecommerce.app.model.*;
import com.fatemeh.ecommerce.app.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    UserRepository userRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthController(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> singup(@RequestBody SignupRequestDTO signupRequestDTO){

        ApiResponse apiResponse = new ApiResponse();

        if (userRepository.findByUsername(signupRequestDTO.getUsername()).isEmpty()){
            String bcPassword = bCryptPasswordEncoder.encode(signupRequestDTO.getPassword());
            User user = new User();
            user.setUsername(signupRequestDTO.getUsername());
            user.setPassword(bcPassword);
            user.setRole(Role.USER);
            userRepository.save(user);
            apiResponse.setMessage("User created!");
            apiResponse.setSuccess(true);
            apiResponse.setTimestamp(LocalDateTime.now());
        } else {
            apiResponse.setMessage("Username already exists");
            apiResponse.setSuccess(false);
            apiResponse.setTimestamp(LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);

    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequestDTO loginRequestDTO){

        ApiResponse apiResponse = new ApiResponse();
        if(userRepository.findByUsername(loginRequestDTO.getUsername()).isPresent()){
            User user = userRepository.findByUsername(loginRequestDTO.getUsername()).get();

            if (bCryptPasswordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())){
                apiResponse.setMessage("Logged in");
                apiResponse.setTimestamp(LocalDateTime.now());
                apiResponse.setSuccess(true);
            } else {
                apiResponse.setMessage("Invalid password");
                apiResponse.setTimestamp(LocalDateTime.now());
                apiResponse.setSuccess(false);
            }
        } else {
            apiResponse.setMessage("User not found");
            apiResponse.setTimestamp(LocalDateTime.now());
            apiResponse.setSuccess(false);
        }
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.UNAUTHORIZED)
                .body(apiResponse);

    }
}
