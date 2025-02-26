package com.example.service;

import com.example.entity.User;
import com.example.repository.UserRepository;
import com.example.request.CreateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class CreateUserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public ResponseEntity<?> createUser(
            @RequestBody CreateUserRequest request) {
//        System.out.println("vao");
        User user = new User(request.getUserID(), request.getName(), request.getDateOfBirth(), request.getAge(), request.getPhone(), request.getUsername(), passwordEncoder.encode(request.getPassword()), request.getRole(), request.getCreateDate());
//        System.out.println("vao : " + user.getName());
        return ResponseEntity.ok(userRepository.insert(user));
    }
}
