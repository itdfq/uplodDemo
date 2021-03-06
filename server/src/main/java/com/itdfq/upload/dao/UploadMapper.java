package com.itdfq.upload.dao;

import com.itdfq.upload.entity.Upload;
import com.itdfq.upload.entity.UploadExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UploadMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table upload
     *
     * @mbg.generated
     */
    long countByExample(UploadExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table upload
     *
     * @mbg.generated
     */
    int deleteByExample(UploadExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table upload
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table upload
     *
     * @mbg.generated
     */
    int insert(Upload record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table upload
     *
     * @mbg.generated
     */
    int insertSelective(Upload record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table upload
     *
     * @mbg.generated
     */
    List<Upload> selectByExample(UploadExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table upload
     *
     * @mbg.generated
     */
    Upload selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table upload
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") Upload record, @Param("example") UploadExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table upload
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") Upload record, @Param("example") UploadExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table upload
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Upload record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table upload
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Upload record);
}