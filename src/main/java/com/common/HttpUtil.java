package com.common;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpUtil {

    public static <T> T post(String path, Object req, Type type, Map<String, String> properties) throws IOException {
        URL url = new URL(path);
        HttpURLConnection connection = ((HttpURLConnection) url.openConnection());
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        if (properties != null) {
            for (Map.Entry<String, String> entry : properties.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }

        byte[] bytes = JSON.toJSONBytes(req);
        try (OutputStream outputStream = connection.getOutputStream()) {
            outputStream.write(bytes);
        }
        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            System.out.println("post error");
            System.out.println(JSON.toJSONString(req));
        }
        return JSON.parseObject(connection.getInputStream(), type);
    }
}
