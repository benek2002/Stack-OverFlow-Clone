package com.benek.controllers;


import com.benek.dtos.SignupDTO;
import com.benek.dtos.UserDTO;
import com.benek.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequiredArgsConstructor
@RestController
public class SignupController {

    private final UserService userService;
    @PostMapping("/sign-up")
    public ResponseEntity<?> createUser(@RequestBody(required = true) SignupDTO signupDTO){

        if(userService.hasUserWithEmail(signupDTO.getEmail())){
            return new ResponseEntity<>("User already exist with this email: " + signupDTO.getEmail(), HttpStatus.NOT_ACCEPTABLE);
        }
       UserDTO createdUserDTO =  userService.createUser(signupDTO);
       if(createdUserDTO == null){
           return new ResponseEntity<>("User not created", HttpStatus.BAD_REQUEST);
       }
       return new ResponseEntity<>(createdUserDTO, HttpStatus.CREATED);
    }
}
