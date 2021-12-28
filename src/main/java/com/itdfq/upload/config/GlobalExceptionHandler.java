package com.itdfq.upload.config;

import com.itdfq.upload.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

/**
 * @Author GocChin
 * @Date 2021/12/28 22:36
 * @Blog: itdfq.com
 * @QQ: 909256107
 * @Description:
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MultipartException.class)
    public @ResponseBody Result handleBusinessException(MaxUploadSizeExceededException ex) {
        String msg;
        if (ex.getCause().getCause() instanceof FileSizeLimitExceededException) {
            log.error(ex.getMessage());
            msg = "上传文件过大[单文件大小不得超过5M]";
        } else if (ex.getCause().getCause() instanceof SizeLimitExceededException) {
            log.error(ex.getMessage());
            msg = "上传文件过大[总上传文件大小不得超过10M]";
        } else {
            msg = "上传文件失败";
        }
        return Result.newFailure(msg);

    }

}
