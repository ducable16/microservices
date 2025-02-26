package com.example.service;

import com.example.ResponseDTO;
import com.example.entity.User;
import com.example.repository.UserRepository;
import com.example.request.CreateUserRequest;
import com.example.request.UpdateUserRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserInfoOperatorService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private CreateUserService createUserService;
    @Autowired
    private DeleteUserService deleteUserService;
    @Autowired
    private UpdateUserService updateUserService;

    @RabbitListener(queues = "order.user.request.queue")
    public void receiveRequest(String jsonRequest) throws JsonProcessingException {
        Map<String, Object> data = objectMapper.readValue(jsonRequest, Map.class);
        String userID = data.get("userID").toString();
        Optional<User> user = userRepository.findByUserID(userID);

        ResponseDTO respDTO = new ResponseDTO();
        respDTO.setRequestID(data.get("requestID").toString());
        respDTO.setUserID(user.get().getUserID());
        respDTO.setName((String) user.get().getName());
        respDTO.setPhone((String) user.get().getPhone());
        rabbitTemplate.convertAndSend("directExchange", "order.user.response", objectMapper.writeValueAsString(respDTO));

    }

    @RabbitListener(queues = "gateway.user.request.queue")
    public void processUserRequest(String jsonRequest) throws JsonProcessingException {
        Map<String, Object> data = objectMapper.readValue(jsonRequest, Map.class);
        String actionType = (String) data.get("actionType");
        switch (actionType) {
            case "GET":
                String userID = data.get("userID").toString();
                Optional<User> user = userRepository.findByUserID(userID);
                if(!user.isPresent()) {
                    Map<String, Object> response = new HashMap<>();
                    response.put("requestID", data.get("requestID").toString());
                    response.put("status", "404");
                    response.put("message", "User Not Found");
                    response.put("userID", userID);
                    rabbitTemplate.convertAndSend("directExchange","gateway.user.response", objectMapper.writeValueAsString(response));
                    break;
                }
                ResponseDTO respDTO = new ResponseDTO();
                respDTO.setRequestID(data.get("requestID").toString());
                respDTO.setUserID(user.get().getUserID());
                respDTO.setName((String) user.get().getName());
                respDTO.setPhone((String) user.get().getPhone());
                rabbitTemplate.convertAndSend("directExchange", "gateway.user.response", objectMapper.writeValueAsString(respDTO));
                break;
            case "REGISTER":
                CreateUserRequest createUserRequest = objectMapper.convertValue(data, CreateUserRequest.class);
                createUserService.createUser(createUserRequest);
                break;
            case "DELETE":
                String userDeletedID = objectMapper.convertValue(data.get("userID"), String.class);
                deleteUserService.deleteUser(userDeletedID);
                break;
            case "UPDATE":
                UpdateUserRequest updateUserRequest = objectMapper.convertValue(data, UpdateUserRequest.class);
                updateUserService.updateUser(updateUserRequest);
                break;
        }
    }

}
