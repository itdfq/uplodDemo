package com.itdfq.upload.controller;

import com.itdfq.upload.biz.UploadBizService;
import com.itdfq.upload.entity.Result;
import com.itdfq.upload.entity.UploadResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * @Author GocChin
 * @Date 2021/12/28 21:34
 * @Blog: itdfq.com
 * @QQ: 909256107
 * @Description:
 */
@RestController
@RequestMapping("/itdfq")
@Slf4j
public class UploadController {

    @Autowired
    private UploadBizService uploadBizService;

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Result upload(@RequestParam(value = "file") MultipartFile file) {
        return uploadBizService.saveFile(file);
    }

    /**
     * 根据Code下载
     * @param code
     * @return
     */
    @GetMapping("/downByCode")
    public Result downByCode(@RequestParam String code, HttpServletResponse response) {
        return uploadBizService.getFile(code,response);
    }

    /**
     * 通过唯一ID 获取文件信息
     *
     * @param code
     * @return
     */
    @GetMapping("/getByCode")
    public Result<UploadResult> getByCode(@RequestParam String code) {
        return uploadBizService.getByCode(code);
    }


}
