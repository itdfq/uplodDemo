package com.itdfq.upload.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author GocChin
 * @Date 2021/12/28 22:48
 * @Blog: itdfq.com
 * @QQ: 909256107
 * @Description:
 */
@Data
public class UploadResult implements Serializable {

    /**
     * 文件原名
     */
    private String name;
    /**
     * 大小 单位：字节
     */
    private Integer size;

    /**
     * 类型
     */
    private String type;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date addTime;
    /**
     * 唯一ID
     */
    private String code;
    /**
     * 文件保存地址
     */
    private String saveAddress;
}
