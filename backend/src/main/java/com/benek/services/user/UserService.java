package com.benek.services.user;


import com.benek.dtos.SignupDTO;
import com.benek.dtos.UserDTO;
import org.springframework.stereotype.Service;


public interface UserService {
    UserDTO createUser(SignupDTO signupDTO);

    boolean hasUserWithEmail(String email);
}
