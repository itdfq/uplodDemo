package com.itdfq.upload;

import com.itdfq.upload.entity.Upload;
import com.itdfq.upload.service.UploadService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @Author GocChin
 * @Date 2021/12/28 21:22
 * @Blog: itdfq.com
 * @QQ: 909256107
 * @Description:
 */
public class UploadServiceTest extends BaseTest{
    @Autowired
    private UploadService uploadService;

    @Test
    public void insert(){
        Upload upload = new Upload();
        upload.setAddTime(new Date());
        upload.setCode(String.valueOf(System.currentTimeMillis()));
        upload.setName("测试");
        upload.setSaveAddress("/projec/upload/20211228/");
        upload.setType("image/jpeg");
        upload.setSize(100);
        upload.setDeletedId(0);
        Integer id = uploadService.insert(upload);
        System.out.println(id);
    }
}
