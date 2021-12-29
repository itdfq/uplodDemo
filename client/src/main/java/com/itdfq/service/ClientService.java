package com.itdfq.service;


import com.itdfq.entity.Result;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.UUID;

/**
 * @Author GocChin
 * @Date 2021/12/29 10:57
 * @Blog: itdfq.com
 * @QQ: 909256107
 * @mark:
 */
public class ClientService {

    /**
     * 默认url
     */
    private static final String URL = "http://121.36.77.21:8082/itdfq/";


    /**
     * 超时时间
     */
    private static final int TIME_OUT = 8 * 1000;
    /**
     * 编码
     */
    private static final String CHARSET = "UTF-8";
    /**
     * 前缀
     */
    private static final String PREFIX = "--";
    /**
     * 边界标识
     */
    private static final String BOUNDARY = UUID.randomUUID().toString();
    /**
     * 类型
     */
    private static final String CONTENT_TYPE = "multipart/form-data";
    /**
     * 换行符号
     */
    private static final String LINE_END = "\r\n";
    /**
     * http状态码200
     */
    private static final Integer SUCCESS_STATE = 200;


    /**
     * 获取上传文件详细信息
     *
     * @param code 唯一UUID
     * @return
     */
    public static String getByCode(String code) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(URL + "getByCode?code=" + code);
            conn = (HttpURLConnection) url.openConnection();
            conn.setUseCaches(false);
            // 请求超时时间
            conn.setConnectTimeout(TIME_OUT);
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            //建立到远程对象的实际连接。
            conn.connect();
            // 获取响应正文
            BufferedReader reader = null;
            StringBuffer resultBuffer = new StringBuffer();
            String tempLine = null;
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), CHARSET));
            while ((tempLine = reader.readLine()) != null) {
                resultBuffer.append(tempLine);
            }
            reader.close();
            conn.disconnect();
            return resultBuffer.toString();
        } catch (MalformedURLException e) {
            return "URL地址异常";
        } catch (IOException e) {
            return "IO流操作异常";
        }
    }

    /**
     * 通过Code获取流
     *
     * @param code
     * @return
     */
    public static Result<InputStream> doGetForStream(String code) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(URL + "downByCode?code=" + code);
            conn = (HttpURLConnection) url.openConnection();
            conn.setUseCaches(false);
            // 请求超时时间
            conn.setConnectTimeout(TIME_OUT);
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setDoOutput(true);
            //建立到远程对象的实际连接。
            conn.connect();
            InputStream input = conn.getInputStream();
            return Result.newSuccess(input);
        } catch (MalformedURLException e) {
            return Result.newFailure("URL地址异常");
        } catch (IOException e) {
            e.printStackTrace();
            return Result.newFailure("IO流操作异常");
        }


    }

    /**
     * post请求上传文件
     *
     * @param strParams 请求参数
     * @param file      请求文件
     * @return
     */
    public static String uplodByPost(Map<String, String> strParams, File file) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(URL + "upload");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //Post 请求不能使用缓存
            conn.setUseCaches(false);
            //设置请求头参数
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", CHARSET);
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);

            //上传参数
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(getParam(strParams).toString());
            dos.flush();
            conn.disconnect();
            //文件上传
            StringBuilder fileSb = new StringBuilder();
            if (file != null) {
                fileSb.append(PREFIX)
                        .append(BOUNDARY)
                        .append(LINE_END)
                        //name里面的值为服务端需要的key 只有这个key 才可以得到对应的文件 filename是文件的名字，包含后缀名的 比如:abc.png
                        .append("Content-Disposition: form-data; name=\"file\"; filename=\""
                                + file.getName() + "\"" + LINE_END)
                        .append("Content-Type: image/png" + LINE_END)
                        .append("Content-Transfer-Encoding: 8bit" + LINE_END)
                        // 参数头设置完以后需要两个换行，然后才是参数内容
                        .append(LINE_END);
                dos.writeBytes(fileSb.toString());
                dos.flush();
                InputStream is = new FileInputStream(file);
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    dos.write(buffer, 0, len);
                }
                is.close();
                dos.writeBytes(LINE_END);
            }
            //请求结束标志
            dos.writeBytes(PREFIX + BOUNDARY + PREFIX + LINE_END);
            dos.flush();
            dos.close();
            //读取服务器返回信息
            if (SUCCESS_STATE.equals(conn.getResponseCode())) {
                InputStream in = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line = null;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                return response.toString();
            }
            return conn.getResponseMessage();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return "上传异常";
    }

    private static StringBuilder getParam(Map<String, String> strParams) {
        StringBuilder strSb = new StringBuilder();
        for (Map.Entry<String, String> entry : strParams.entrySet()) {
            strSb.append(PREFIX)
                    .append(BOUNDARY)
                    .append(LINE_END).append("Content-Disposition: form-data; name=\"").append(entry.getKey()).append("\"").append(LINE_END)
                    .append("Content-Type: text/plain; charset=" + CHARSET + LINE_END)
                    .append("Content-Transfer-Encoding: 8bit" + LINE_END)
                    // 参数头设置完以后需要两个换行，然后才是参数内容
                    .append(LINE_END)
                    .append(entry.getValue())
                    .append(LINE_END);
        }
        return strSb;
    }

    /**
     * 将流转为文件
     *
     * @param inputStream
     * @param file
     * @throws IOException
     */
    public static void copyInputStreamToFile(InputStream inputStream, File file) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            int read;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        }

    }


}
