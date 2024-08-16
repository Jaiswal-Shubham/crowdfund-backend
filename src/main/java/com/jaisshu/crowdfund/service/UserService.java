package com.jaisshu.crowdfund.service;

import com.jaisshu.crowdfund.dto.UserLoginDTO;
import com.jaisshu.crowdfund.dto.UserRegistrationDTO;
import com.jaisshu.crowdfund.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User registerUser(UserRegistrationDTO userRegistrationDTO);
    Optional<User> findByEmail(String email);

    List<User> getAllUsers();

    User loginUser(UserLoginDTO userLoginDTO);
}