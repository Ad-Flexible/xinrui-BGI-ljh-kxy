package org.xinrui.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.xinrui.entity.SampleInfo;
import org.xinrui.mapper.SampleInfoMapper;
import org.xinrui.mapper.SampleMapper;
import org.xinrui.service.LisSampleService;
import org.xinrui.service.SampleService;

@Service
public class LisSampleServiceImpl extends ServiceImpl<SampleInfoMapper, SampleInfo> implements LisSampleService {
}
