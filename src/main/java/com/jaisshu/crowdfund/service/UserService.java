package com.jaisshu.crowdfund.service;

import com.jaisshu.crowdfund.dto.UserDTO;
import com.jaisshu.crowdfund.dto.UserLoginRequestDTO;
import com.jaisshu.crowdfund.dto.UserLoginResponseDTO;
import com.jaisshu.crowdfund.dto.UserRegistrationDTO;
import com.jaisshu.crowdfund.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User registerUser(UserRegistrationDTO userRegistrationDTO);
    Optional<User> findByEmail(String email);

    List<User> getAllUsers();

    UserLoginResponseDTO loginUser(UserLoginRequestDTO userLoginRequestDTO);
}