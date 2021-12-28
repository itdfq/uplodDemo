package com.itdfq.upload.biz;

import com.itdfq.upload.constant.UploadConstant;
import com.itdfq.upload.entity.Result;
import com.itdfq.upload.entity.Upload;
import com.itdfq.upload.entity.UploadResult;
import com.itdfq.upload.service.UploadService;
import com.itdfq.upload.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /**
     * 文件上传数据记录
     *
     * @param size
     * @param name
     * @param type
     * @return
     */
    public Result<String> insert(Integer size, String name, String type) {
        Upload upload = new Upload();
        upload.setDeletedId(UploadConstant.DELETED_ID);
        upload.setSize(size);
        upload.setName(name);
        upload.setType(type);
        String code = String.valueOf(System.currentTimeMillis());
        upload.setCode(code);
        upload.setSaveAddress(UploadConstant.PROJECT_HOME + UploadConstant.SEPARATOR +
                DateUtils.getFormattedString(new Date(), DateUtils.DATE_FORMAT_1));
        Integer id = uploadService.insert(upload);
        if (id == null) {
            log.error("上传文件异常,name：【{}】,type:【{}】", name, type);
            return Result.newFailure("上传异常");
        }
        return Result.newSuccess(code);

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
