package com.example;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class OrderIDGenerateService {
    public static String generateOrderID() {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String uuidPart = UUID.randomUUID().toString().substring(0, 6);
        return timestamp + uuidPart;
    }
}
