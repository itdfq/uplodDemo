package com.itdfq.upload.controller;

import com.itdfq.upload.biz.UploadBizService;
import com.itdfq.upload.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author GocChin
 * @Date 2021/12/28 21:34
 * @Blog: itdfq.com
 * @QQ: 909256107
 * @Description:
 */
@RestController
@RequestMapping("/itdfq")
public class UploadController {

    @Autowired
    private UploadBizService uploadBizService;

    @PostMapping("/upload")
    public Result upload(@RequestParam(value = "file") MultipartFile file) {
        if (file == null) {
            return Result.newFailure("文件不能为空");
        }
        String type = file.getContentType();
        String name = file.getOriginalFilename();
        Integer size = Math.toIntExact(file.getSize());
        Map<String, String> map = new HashMap<>();
        map.put("type", type);
        map.put("name", name);

        map.put("size",String.valueOf(size));
        return Result.newSuccess(map);

    }
}
