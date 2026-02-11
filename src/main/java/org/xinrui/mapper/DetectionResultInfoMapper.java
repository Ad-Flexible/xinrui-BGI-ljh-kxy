package org.xinrui.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.xinrui.entity.DetectionResultInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DetectionResultInfoMapper extends BaseMapper<DetectionResultInfo> {
    // 可扩展自定义查询方法
}
