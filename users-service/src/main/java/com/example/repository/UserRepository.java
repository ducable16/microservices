package com.example.repository;

import com.example.entity.Role;
import com.example.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {
    Optional<User> findByUserID(String userID);

    Optional<User> deleteUserByUserID(String userID);

    Optional<User> findByUsername(String account);

    List<User> findAllByRole(Role role);

}
