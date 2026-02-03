package org.xinrui.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.xinrui.entity.ResultInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ResultInfoMapper extends BaseMapper<ResultInfo> {
    // 可扩展自定义查询方法
}
