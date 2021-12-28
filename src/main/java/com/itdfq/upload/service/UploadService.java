package com.itdfq.upload.service;

import com.itdfq.upload.constant.UploadConstant;
import com.itdfq.upload.dao.UploadMapper;
import com.itdfq.upload.entity.Upload;
import com.itdfq.upload.entity.UploadExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author GocChin
 * @Date 2021/12/28 21:18
 * @Blog: itdfq.com
 * @QQ: 909256107
 * @Description:
 */
@Service
public class UploadService {
    @Autowired
    private UploadMapper uploadMapper;

    public Integer insert(Upload upload) {
        uploadMapper.insert(upload);
        return upload.getId();
    }


    /**
     * 通过code获取记录
     *
     * @param code
     * @return
     */
    public Upload getByCode(String code) {
        UploadExample example = new UploadExample();
        UploadExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedIdEqualTo(UploadConstant.DELETED_ID).andCodeEqualTo(code);
        List<Upload> uploads = uploadMapper.selectByExample(example);
        return uploads.stream().findFirst().orElse(null);
    }
}
