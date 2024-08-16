package com.jaisshu.crowdfund.service.impl;

import com.jaisshu.crowdfund.dto.UserLoginDTO;
import com.jaisshu.crowdfund.dto.UserRegistrationDTO;
import com.jaisshu.crowdfund.entity.User;
import com.jaisshu.crowdfund.exception.RegisterUserDatabaseException;
import com.jaisshu.crowdfund.exception.InvalidCredentialException;
import com.jaisshu.crowdfund.repository.UserRepository;
import com.jaisshu.crowdfund.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User registerUser(UserRegistrationDTO userRegistrationDTO) {
        try {
            UUID userId = UUID.randomUUID();
            User user = User.builder()
                    .firstName(userRegistrationDTO.getFirstName())
                    .lastName(userRegistrationDTO.getLastName())
                    .email(userRegistrationDTO.getEmail())
                    .password(userRegistrationDTO.getPassword())
                    .role(userRegistrationDTO.getRole().name())
                    .userId(userId)
                    .build();
            return userRepository.save(user);
        }
        catch(Exception ex){
            throw new RegisterUserDatabaseException(ex.getMessage());
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User loginUser(UserLoginDTO userLoginDTO) {
        Optional<User> userOpt = userRepository.getLoggedInUser(userLoginDTO.getEmail(),userLoginDTO.getPassword());
        if(userOpt.isPresent()){
            return userOpt.get();
        }
        else{
            throw new InvalidCredentialException("Invalid email or password");
        }
    }
}