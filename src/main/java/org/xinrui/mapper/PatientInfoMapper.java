package org.xinrui.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.xinrui.entity.PatientInfo;

@Mapper
public interface PatientInfoMapper extends BaseMapper<PatientInfo> {

    @Override
    PatientInfo selectOne(Wrapper<PatientInfo> queryWrapper);
}