package com.example;

public class Request {
    private String method;
    private String url;
    private String body;
    public Request(String method, String url, String body) {}
    public String getMethod() {
        return method;
    }
}
