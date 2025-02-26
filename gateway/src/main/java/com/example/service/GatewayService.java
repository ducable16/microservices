package com.example.service;

import com.example.RequestID;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;

@Service
public class GatewayService {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    ObjectMapper objectMapper;

    @RabbitListener(queues = "gateway.user.response.queue")
    public void receiveUserInfoResponse(String jsonResponse) throws JsonProcessingException, InterruptedException {
        Map<String, Object> result = objectMapper.readValue(jsonResponse, Map.class);
        DeferredResult<Map<String, Object>> deferredResult = RequestID.map.get(result.get("requestID"));
        deferredResult.setResult(result);
        RequestID.map.remove(result.get("requestID"));
    }
    public void sendUserRequest(String actionType, Map<String, Object> requestData) throws JsonProcessingException, InterruptedException {

        requestData.put("actionType", actionType);
        String jsonRequest = objectMapper.writeValueAsString(requestData);
        rabbitTemplate.convertAndSend("directExchange", "gateway.user.request", jsonRequest);
    }


    public void sendOrderRequest(Map<String, Object> requestData) throws JsonProcessingException, InterruptedException {
        String jsonRequest = objectMapper.writeValueAsString(requestData);
        rabbitTemplate.convertAndSend("directExchange", "gateway.order.request", jsonRequest);
    }

    @RabbitListener(queues = "gateway.order.response.queue")
    public void receiveOrderResponse(String jsonResponse) throws JsonProcessingException, InterruptedException {
        Map<String, Object> result = objectMapper.readValue(jsonResponse, Map.class);
        String requestID = result.get("requestID").toString();

        DeferredResult<Map<String, Object>> deferredResult = RequestID.map.get(requestID);
        if(deferredResult != null) {
            deferredResult.setResult(result);
            RequestID.map.remove(result.get("requestID"));
        }
    }
}