package com.itdfq.upload.biz;

import com.itdfq.upload.constant.UploadConstant;
import com.itdfq.upload.entity.Result;
import com.itdfq.upload.entity.Upload;
import com.itdfq.upload.entity.UploadResult;
import com.itdfq.upload.service.UploadService;
import com.itdfq.upload.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

/**
 * @Author GocChin
 * @Date 2021/12/28 21:52
 * @Blog: itdfq.com
 * @QQ: 909256107
 * @Description:
 */
@Service
@Slf4j
public class UploadBizService {
    @Autowired
    private UploadService uploadService;

    @Value("${upload.address}")
    private String uploadAddress;

    /**
     * 文件上传数据记录
     *
     * @param size
     * @param name
     * @param type
     * @return
     */
    public Result<String> insert(Integer size, String name, String type, String saveAddress, Long uuid) {
        Upload upload = new Upload();
        upload.setDeletedId(UploadConstant.DELETED_ID);
        upload.setSize(size);
        upload.setName(name);
        upload.setType(type);
        String code = String.valueOf(uuid);
        upload.setCode(code);
        upload.setAddTime(new Date());
        upload.setSaveAddress(saveAddress);
        Integer id = uploadService.insert(upload);
        if (id == null) {
            log.error("插入数据异常,name：【{}】,type:【{}】,保存地址：【{}】", name, type, saveAddress);
            return Result.newFailure("插入异常");
        }
        return Result.newSuccess(code);

    }


    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    public Result saveFile(MultipartFile file) {
        if (file == null) {
            return Result.newFailure("文件不能为空");
        }
        String absolutePath = null;
        long millis = System.currentTimeMillis();
        try {
            String address = uploadAddress + UploadConstant.SEPARATOR_1 +
                    DateUtils.getFormattedString(new Date(), DateUtils.DATE_FORMAT_1);
            // 文件保存路径
            File parent = new File(address);
            //如果文件夹不存在则新建
            if (!parent.exists()) {
                parent.mkdirs();
            }
            int index = file.getOriginalFilename().lastIndexOf('.');
            String name = index == -1 ? UploadConstant.EMPTY_STRING : file.getOriginalFilename().substring(0, index);
            String type = index == -1 ? UploadConstant.EMPTY_STRING : file.getOriginalFilename().substring(index);
            String filename = name + UploadConstant.SEPARATOR_2 + millis + type;
            File dest = new File(parent, filename);
            absolutePath = dest.getAbsolutePath();
            // 数据传输
            file.transferTo(dest);
        } catch (IOException e) {
            log.error("文件转存异常", e);
            return Result.newFailure("文件上传失败");
        }
        return insert((int) file.getSize(), file.getOriginalFilename(), file.getContentType(), absolutePath, millis);

    }

    /**
     * 通过唯一ID获取文件流
     *
     * @param code
     * @return
     */
    public void getFile(String code, HttpServletResponse response) throws UnsupportedEncodingException {
        Upload upload = uploadService.getByCode(code);
        if (upload == null) {
            throw new RuntimeException("文件不存在");
        }
        File file = new File(upload.getSaveAddress());
        //设置文件ContentType类型
        response.setContentType("multipart/form-data");
        //设置文件头
        log.info("文件名字：{}", file.getName());
        //设置编码
        String fileName = URLEncoder.encode(file.getName(), "UTF-8");
        response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
        ServletOutputStream out = null;
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            //获取ServletOutputStream对象(out)
            out = response.getOutputStream();
            int b = 0;
            byte[] buffer = new byte[512];
            while (b != -1) {
                b = inputStream.read(buffer);
                if (b != -1) {
                    //写到输出流(out)中
                    out.write(buffer, 0, b);
                }
            }

        } catch (IOException e) {
            log.error("获取文件异常", e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (out != null) {
                    out.close();
                    out.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 根据UUId获取文件数据
     *
     * @param code
     * @return
     */
    public Result<UploadResult> getByCode(String code) {
        try {
            Upload upload = uploadService.getByCode(code);
            return Result.newSuccess(format(upload));
        } catch (Exception e) {
            log.error("查询异常,code:{}", code, e);
            return Result.newFailure("查询异常");
        }
    }

    private UploadResult format(Upload upload) {
        UploadResult result = new UploadResult();
        result.setAddTime(upload.getAddTime());
        result.setCode(upload.getCode());
        result.setName(upload.getName());
        result.setSaveAddress(upload.getSaveAddress());
        result.setSize(upload.getSize());
        result.setType(upload.getType());
        return result;

    }
}
