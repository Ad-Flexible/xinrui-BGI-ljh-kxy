package org.xinrui.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.xinrui.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.xinrui.entity.TestResultInfo;

@Mapper
public interface DetectionResultInfoMapper extends BaseMapper<TestResultInfo> {
    // 可扩展自定义查询方法
}
