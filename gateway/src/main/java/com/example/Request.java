package com.example;

public class Request {
    private String method;
    private String url;
    private String body;
    public Request(String method, String url, String body) {}
    public String getMethod() {
        return method;
    }
    public void setMethod(String method) {
        this.method = method;
    }
    public String getUrl() {
        return url;
    }
}
