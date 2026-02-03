package org.xinrui.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xinrui.dto.RequestSampleResponseDTO;
import org.xinrui.entity.SampleInfo;
import org.xinrui.exception.BusinessException;
import org.xinrui.mapper.SampleInfoMapper;
import org.xinrui.service.RequestSampleService;
import org.xinrui.util.BeanConvertUtil;

@Slf4j
@Service
public class RequestSampleServiceImpl implements RequestSampleService {

    @Autowired
    private SampleInfoMapper sampleInfoMapper;

    @Override
    @Transactional(readOnly = true)
    public RequestSampleResponseDTO getSampleInfo(String oldSampleNum) {
        log.info("查询样本信息，原样本编号: {}", oldSampleNum);

        // 1. 参数校验（已在Controller层校验，此处可省略）
        if (oldSampleNum == null || oldSampleNum.trim().isEmpty()) {
            throw new BusinessException("100909140", "原样本编号不能为空");
        }

        // 2. 查询数据库（优先使用唯一索引字段）
        SampleInfo sampleInfo = sampleInfoMapper.selectByOldSampleNum(oldSampleNum);
        if (sampleInfo == null) {
            log.warn("样本信息不存在，原样本编号: {}", oldSampleNum);
            throw new BusinessException("100909141", "样本信息不存在");
        }

        // 3. 状态校验（示例：仅允许查询已完成收样的样本）
        if (sampleInfo.getStatus() == null || sampleInfo.getStatus() < 1) {
            throw new BusinessException("100909142", "样本状态异常，暂不可查询");
        }

        // 4. 转换为响应DTO
        RequestSampleResponseDTO responseDTO = BeanConvertUtil.convert(sampleInfo, RequestSampleResponseDTO.class);

        // 5. 敏感信息脱敏（按需）
        // if (responseDTO.getPatientIdCard() != null) {
        //     responseDTO.setPatientIdCard(IdCardUtil.desensitize(responseDTO.getPatientIdCard()));
        // }

        log.info("样本信息查询成功，样本编号: {}", responseDTO.getSampleId());
        return responseDTO;
    }
}
