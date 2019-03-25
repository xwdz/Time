package com.xwdz.site.utils;


import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class CommUtils {


    public static boolean isEmpty(String content) {
        return content == null || content.length() == 0;
    }

    public static String generateId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String encode(String text) {
        return new String(text.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
    }

}
