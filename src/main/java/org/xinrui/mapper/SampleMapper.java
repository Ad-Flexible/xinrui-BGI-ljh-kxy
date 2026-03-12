package org.xinrui.mapper;


import org.xinrui.dto.SampleDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.xinrui.dto.SampleRegistrationDto;

import java.time.LocalDate;

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

    LocalDate selectCollectDateBySAId(@Param("screeningArchivesId") Long screeningArchivesId);

    /**
     * 根据筛查档案 ID 查询样本登记信息（多表联查）
     * @param screeningArchivesId 筛查档案 ID
     * @return 样本登记 DTO
     */
    SampleRegistrationDto selectSampleRegistrationBySAId(Long screeningArchivesId);

}