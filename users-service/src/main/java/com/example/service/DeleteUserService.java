package com.example.service;

import com.example.entity.User;
import com.example.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Service
public class DeleteUserService {
    @Autowired
    UserRepository userRepository;

    public ResponseEntity<?> deleteUser(
            @RequestParam String userID) {
        User user = userRepository.findByUserID(userID).get();
//        if(!user.isPresent()) return ResponseEntity.status(404).body("Account not found");
        return ResponseEntity.ok(userRepository.deleteUserByUserID(userID));
    }
}
