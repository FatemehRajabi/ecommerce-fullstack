package com.fatemeh.ecommerce.app.controller;

import com.fatemeh.ecommerce.app.model.ApiResponse;
import com.fatemeh.ecommerce.app.model.Role;
import com.fatemeh.ecommerce.app.model.SignupRequestDTO;
import com.fatemeh.ecommerce.app.model.User;
import com.fatemeh.ecommerce.app.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
