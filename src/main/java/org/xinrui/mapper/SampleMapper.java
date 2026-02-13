package org.xinrui.mapper;

import org.xinrui.dto.SampleDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * RequestSampleDao类，用于Halos请求样本信息的数据库操作
 * 该类包含与请求样本信息相关的数据访问方法
 */
@Mapper
public interface SampleMapper {

    /**
     * 根据原样本编号查询（唯一索引）
     */
    SampleDto selectByOldSampleNum(@Param("oldSampleNum") String oldSampleNum);
}