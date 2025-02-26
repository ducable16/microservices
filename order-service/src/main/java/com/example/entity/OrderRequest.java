package com.example.entity;

import java.util.List;

public class OrderRequest {
    private String requestID;
    private String userID;
    private String name;
    private String phone;
    private List<OrderProduct> items;
    private double totalPrice;
    public OrderRequest() {
    }

    public OrderRequest(String requestID, String userID, String name, String phone, List<OrderProduct> items, double totalPrice) {
        this.requestID = requestID;
        this.userID = userID;
        this.name = name;
        this.phone = phone;
        this.items = items;
        this.totalPrice = totalPrice;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setCustomerName(String name) {
        this.name = name;
    }

    public List<OrderProduct> getItems() {
        return items;
    }

    public void setItems(List<OrderProduct> items) {
        this.items = items;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
