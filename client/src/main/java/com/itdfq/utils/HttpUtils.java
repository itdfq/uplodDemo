package com.itdfq.utils;


import org.junit.platform.commons.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * @author: QianMo
 * @date: 2021/12/29 10:57
 * @mark:
 */
public class HttpUtils {
    public static void doPost() {

    }

    /**
     * "http://localhost:8088/dicArea/dataListByRegion/传的参数值/传的参数值";
     * 传入的参数不要key,直接把value放到地址后边用/间隔
     *
     * @author ldb
     * 2020-12-26
     */
    public static String doGet(String requestUrl, String token, String param) {
        try {
            URL url = new URL(requestUrl);
            URLConnection conn = url.openConnection();
            conn.setUseCaches(false);
            // 请求超时时间
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded;charset=UTF-8");
            if (StringUtils.isNotBlank(token)) {
                conn.setRequestProperty("token", token);
            }
            //建立到远程对象的实际连接。
            conn.connect();
            // 4.远程对象变为可用。远程对象的头字段和内容变为可访问。
            // 4.1获取响应的头字段
            Map<String, List<String>> headers = conn.getHeaderFields();
            System.out.println(headers); // 输出头字段

            // 4.2获取响应正文
            BufferedReader reader = null;
            StringBuffer resultBuffer = new StringBuffer();
            String tempLine = null;

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            while ((tempLine = reader.readLine()) != null) {
                resultBuffer.append(tempLine);
            }
            // System.out.println(resultBuffer);
            reader.close();
            return resultBuffer.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        return null;

    }

}
