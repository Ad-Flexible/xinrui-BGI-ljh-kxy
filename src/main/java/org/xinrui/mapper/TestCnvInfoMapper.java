package org.xinrui.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.xinrui.entity.TestCnvInfo;

import java.util.List;

@Mapper
public interface TestCnvInfoMapper extends BaseMapper<TestCnvInfo> {
    void insertBatch(List<TestCnvInfo> list);
}
