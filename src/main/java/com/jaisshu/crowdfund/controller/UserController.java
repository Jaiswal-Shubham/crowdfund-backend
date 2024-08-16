package com.jaisshu.crowdfund.controller;

import com.jaisshu.crowdfund.dto.UserDTO;
import com.jaisshu.crowdfund.dto.UserLoginDTO;
import com.jaisshu.crowdfund.dto.UserRegistrationDTO;
import com.jaisshu.crowdfund.entity.User;
import com.jaisshu.crowdfund.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserDTO> loginUser(@RequestBody UserLoginDTO userLoginDTO) {
        User loggedInUser = userService.loginUser(userLoginDTO);
        return ResponseEntity.ok(loggedInUser.getDto());
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        User registeredUser = userService.registerUser(userRegistrationDTO);
        return ResponseEntity.ok(registeredUser.getDto());
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {

        Optional<User> userOpt = userService.findByEmail(email);
        return userOpt.map(user -> ResponseEntity.ok(user.getDto()))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping("all")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<User> users = userService.getAllUsers();
        if(!users.isEmpty()){
            List<UserDTO> usersDto = users.stream().map(User::getDto).toList();
            return ResponseEntity.ok(usersDto);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
}