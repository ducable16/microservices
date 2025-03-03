package com.example;

public class Request {
    private String method;
    private String url;
    private String body;

    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }

    public Request() {
    }

}
