package com.itdfq.upload;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.itdfq.upload.dao")
public class UploadApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(UploadApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
