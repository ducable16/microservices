package com.example;

import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.Map;

public class RequestID {
    public static Map<String, DeferredResult<Map<String, Object>>> map = new HashMap<String, DeferredResult<Map<String, Object>>>();
}
