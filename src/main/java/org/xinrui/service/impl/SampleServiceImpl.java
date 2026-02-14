package org.xinrui.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xinrui.dto.SampleDto;
import org.xinrui.entity.SampleInfo;
import org.xinrui.exception.BusinessException;
import org.xinrui.mapper.SampleMapper;
import org.xinrui.service.SampleService;
import org.xinrui.util.SampleUtil;

@Slf4j
@Service
public class SampleServiceImpl implements SampleService {

    @Autowired
    private SampleMapper sampleMapper;



    @Override
    @Transactional(readOnly = true)
    public SampleDto getSample(String oldSampleNum) {
        log.info("查询样本信息，原样本编号: {}", oldSampleNum);

        // 1. 查询数据库（优先使用唯一索引字段）
        SampleDto sampleDto = sampleMapper.selectByOldSampleNum(oldSampleNum);
        if (sampleDto == null) {
            log.warn("样本信息不存在，原样本编号: {}", oldSampleNum);
            throw new BusinessException("100909141", "样本信息不存在");
        }

        //2.属性的校验，确保部分字段的必填以及数值的规定
        SampleUtil.RequestSampleValidate(sampleDto);

        log.info("样本信息查询成功，样本编号: {}", sampleDto.getSampleId());
        return sampleDto;
    }
}
