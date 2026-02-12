package org.xinrui.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.xinrui.entity.TestResultInfo;

@Mapper
public interface TestResultInfoMapper extends BaseMapper<TestResultInfo> {
    // 可扩展自定义查询方法
}
