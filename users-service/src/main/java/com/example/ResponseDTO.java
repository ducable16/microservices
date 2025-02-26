package com.example;

public class ResponseDTO {
    private String requestID;
    private String userID;
    private String name;
    private String phone;

    public ResponseDTO(String requestID, String name, String userID, String phone) {
        this.requestID = requestID;
        this.name = name;
        this.userID = userID;
        this.phone = phone;
    }
    public ResponseDTO() {}

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

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
