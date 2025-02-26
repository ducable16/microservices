package com.example.service;

import com.example.entity.User;
import com.example.repository.UserRepository;
import com.example.request.UpdateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UpdateUserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    public ResponseEntity<?> updateUser(
            @RequestBody UpdateUserRequest request) {

        User user = new User(request.getUserID(), request.getName(), request.getDateOfBirth(), request.getAge(), request.getPhone(), request.getUsername(), passwordEncoder.encode(request.getPassword()), request.getRole(), request.getCreateDate());

        return ResponseEntity.ok(userRepository.save(user));
    }
}
