package com.example.controller;

import com.example.entity.OrderProduct;
import com.example.entity.OrderRequest;
import com.example.entity.Product;
import com.example.entity.Response;
import com.example.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderController {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    ObjectMapper objectMapper;

    @RabbitListener(queues = "gateway.order.request.queue")
    public void processOrderRequest(String jsonRequest) throws JsonProcessingException, InterruptedException {
        Map<String, Object> data = objectMapper.readValue(jsonRequest, Map.class);
        String userID = (String) data.get("userID");
        List<OrderProduct> orderProducts = objectMapper.convertValue(data.get("items"), new TypeReference<List<OrderProduct>>() {});
        for(OrderProduct orderProduct : orderProducts) {
            Product temp = productRepository.findByProductName(orderProduct.getProductName()).get();
            orderProduct.setProductID(temp.getProductID());
            orderProduct.setPrice(temp.getPrice());
        }
        Response.map.put(data.get("requestID").toString(), orderProducts);

        Map<String, Object> request = new HashMap<>();
        request.put("userID", userID);
        request.put("requestID", data.get("requestID"));

        rabbitTemplate.convertAndSend("directExchange", "order.user.request", objectMapper.writeValueAsString(request));
    }

    @RabbitListener(queues = "order.user.response.queue")
    public void receiveUserInfoResponse(String jsonResponse) throws JsonProcessingException, InterruptedException {
        Map<String, Object> userInfo = objectMapper.readValue(jsonResponse.toString(), Map.class);
//
        List<OrderProduct> orderProducts = Response.map.get(userInfo.get("requestID"));
        double totalPrice = 0;
        for(OrderProduct orderProduct : orderProducts) {
            totalPrice += orderProduct.getPrice() * orderProduct.getQuantity();
        }
        OrderRequest orderRequest = new OrderRequest(userInfo.get("requestID").toString(),userInfo.get("userID").toString(), userInfo.get("name").toString(), userInfo.get("phone").toString(), orderProducts, totalPrice);
        rabbitTemplate.convertAndSend("directExchange", "gateway.order.response", objectMapper.writeValueAsString(orderRequest));
    }

}
