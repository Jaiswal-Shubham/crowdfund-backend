package com.jaisshu.crowdfund.service.impl;

import com.jaisshu.crowdfund.dto.UserDTO;
import com.jaisshu.crowdfund.dto.UserLoginRequestDTO;
import com.jaisshu.crowdfund.dto.UserLoginResponseDTO;
import com.jaisshu.crowdfund.dto.UserRegistrationDTO;
import com.jaisshu.crowdfund.entity.User;
import com.jaisshu.crowdfund.enums.UserRole;
import com.jaisshu.crowdfund.exception.RegisterUserDatabaseException;
import com.jaisshu.crowdfund.exception.InvalidCredentialException;
import com.jaisshu.crowdfund.repository.UserRepository;
import com.jaisshu.crowdfund.service.UserService;
import com.jaisshu.crowdfund.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

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
    public UserLoginResponseDTO loginUser(UserLoginRequestDTO userLoginRequestDTO) {
        Optional<User> userOpt = userRepository.getLoggedInUser(userLoginRequestDTO.getEmail(), userLoginRequestDTO.getPassword());
        if(userOpt.isPresent()){
            User user = userOpt.get();
            String token = jwtUtil.generateToken(user.getUserId().toString());

            UserLoginResponseDTO userDto = UserLoginResponseDTO.builder()
                    .id(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .role(UserRole.getRole(user.getRole()))
                    .userId(user.getUserId())
                    .token(token)
                    .build();
            userDto.setToken(token);
            return userDto;
        }
        else{
            throw new InvalidCredentialException("Invalid email or password");
        }
    }
}