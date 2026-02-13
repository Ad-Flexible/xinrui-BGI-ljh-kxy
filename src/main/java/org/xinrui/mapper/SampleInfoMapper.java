package org.xinrui.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import org.xinrui.entity.SampleInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * RequestSampleDao类，用于Halos请求样本信息的数据库操作
 * 该类包含与请求样本信息相关的数据访问方法
 */
@Mapper
public interface SampleInfoMapper extends BaseMapper<SampleInfo> {
    @Override
    SampleInfo selectOne(Wrapper<SampleInfo> queryWrapper);
}