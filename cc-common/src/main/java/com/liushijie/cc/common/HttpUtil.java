package com.liushijie.cc.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * Created by liushijie on 6/11/17.
 */
public class HttpUtil {
    public static String invokeHttp(String urlStr) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));

            String line;
            StringBuilder builder = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            reader.close();
            connection.disconnect();

            return builder.toString();
        } catch (IOException e) {
            throw new RuntimeException("Get data error from url[" + urlStr + "]:" + e.getMessage(), e);
        }

    }


    public static void main(String[] args) {
        System.out.println(invokeHttp("http://localhost:8080/config/get?group=test&dataId=test"));
    }
}
