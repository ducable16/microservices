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
    public Request(String method, String url, String body) {
        this.method = method;
        this.url = url;
        this.body = body;
    }

}
