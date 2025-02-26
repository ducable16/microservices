package com.example.controller;

import com.example.OrderIDGenerateService;
import com.example.RequestID;
import com.example.service.GatewayService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class GatewayController {

    @Autowired
    private GatewayService gatewayService;

//    @PostMapping("/register")
//    public Map<String, Object> registerUser(@RequestBody Map<String, Object> requestData) throws JsonProcessingException, InterruptedException {
//        return gatewayService.sendUserRequest("REGISTER", requestData);
//    }
//
//    @PutMapping("/update")
//    public Map<String, Object> updateUser(@RequestBody Map<String, Object> requestData) throws JsonProcessingException, InterruptedException {
//        return gatewayService.sendUserRequest("UPDATE", requestData);
//    }
//
//    @PostMapping("/delete")
//    public Map<String, Object> deleteUser(@RequestBody Map<String, Object> requestData) throws JsonProcessingException, InterruptedException {
//        return gatewayService.sendUserRequest("DELETE", requestData);
//    }
//    @GetMapping("/get-user")
//    public ResponseEntity<?> getUser(@RequestParam Map<String, Object> userID) throws JsonProcessingException, InterruptedException {
//        return ResponseEntity.ok(gatewayService.sendUserRequest("GET", userID));
//    }
    @GetMapping("/order")
    public DeferredResult<Map<String, Object>> getOrder(@RequestBody Map<String, Object> requestData) throws JsonProcessingException, InterruptedException {
        String requestID = OrderIDGenerateService.generateOrderID();
        DeferredResult<Map<String, Object>> deferredResult = new DeferredResult<>(5000L); // Timeout 5s

        deferredResult.onTimeout(() -> {
            Map<String, Object> result = new HashMap<>();
            result.put("status", "Request timed out");
            deferredResult.setResult(result);
        });
        System.out.println(requestID);
        RequestID.map.put(requestID, deferredResult);
        requestData.put("requestID", requestID);
        gatewayService.sendOrderRequest(requestData);
        return deferredResult;
    }

    @GetMapping("/get-user")
    public DeferredResult<Map<String, Object>> getOrderStatus(@RequestParam String userID) throws JsonProcessingException, InterruptedException {
        String requestID = OrderIDGenerateService.generateOrderID();
        DeferredResult<Map<String, Object>> deferredResult = new DeferredResult<>(5000L); // Timeout 5s

        deferredResult.onTimeout(() -> {
            Map<String, Object> result = new HashMap<>();
            result.put("status", "Request timed out");
            deferredResult.setResult(result);
        });

        RequestID.map.put(requestID, deferredResult);
        Map<String, Object> request = new HashMap<>();
        request.put("requestID", requestID);
        request.put("userID", userID);
        gatewayService.sendUserRequest("GET", request);
        return deferredResult;
    }
}
