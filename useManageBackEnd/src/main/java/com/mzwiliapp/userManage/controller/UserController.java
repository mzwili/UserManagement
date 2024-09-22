package com.mzwiliapp.userManage.controller;

import com.mzwiliapp.userManage.exception.UserNotFoundException;
import com.mzwiliapp.userManage.model.User;
import com.mzwiliapp.userManage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/addUser")
    User newUser(@RequestBody User newUser){
        return userRepository.save(newUser);
    }

    @GetMapping("/allUsers")
    List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    User getUserById(@PathVariable Long id){
        return userRepository.findById(id).orElseThrow(()-> new UserNotFoundException(id));
    }
}
