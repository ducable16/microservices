package com.example;

public class Request {
    private String method;
    private String url;
    private String body;

    public Request(String method, String url, String body) {
        this.method = method;
        this.url = url;
        this.body = body;
    }
    public String getMethod() {
        return method;
    }
    public void setMethod(String method) {
        this.method = method;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getBody() {
        return body;
    }


}
