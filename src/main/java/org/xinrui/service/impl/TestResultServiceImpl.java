package org.xinrui.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xinrui.dto.testresult.TestResultDto;
import org.xinrui.entity.*;
import org.xinrui.entity.SampleInfo;
import org.xinrui.exception.BusinessException;
import org.xinrui.mapper.TestResultInfoMapper;
import org.xinrui.mapper.SampleMapper;
import org.xinrui.service.TestResultService;
import org.xinrui.util.TestResultUtil;

@Slf4j
@Service
public class TestResultServiceImpl implements TestResultService {

    @Autowired
    private TestResultInfoMapper resultInfoMapper;

    @Autowired
    private SampleMapper sampleMapper;

    @Autowired
    private TestResultUtil pushResultUtil;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean handlePushResult(TestResultDto requestDTO) {
        log.info("处理Halos推送结果，样本编号: {}", requestDTO.getSampleId());

        try {
            // 1. 验证样本是否存在
            SampleInfo sampleInfo = sampleMapper.selectOne(
                    Wrappers.lambdaQuery(SampleInfo.class)
                            .eq(SampleInfo::getSampleId, requestDTO.getSampleId())
                            .or()
                            //todo 旧样本号是否可以匹配
                            .eq(SampleInfo::getOldSampleNum, requestDTO.getOldSampleNum())
            );

            if (sampleInfo == null) {
                throw new BusinessException("200909140", "样本信息不存在，无法保存结果");
            }

            // 2. 检查是否已存在结果（避免重复推送覆盖）
            TestResultInfo existResult = resultInfoMapper.selectOne(
                    Wrappers.lambdaQuery(TestResultInfo.class)
                            .eq(TestResultInfo::getSampleId, requestDTO.getSampleId())
                            .orderByDesc(TestResultInfo::getCreateTime)
                            .last("LIMIT 1")
            );

            if (existResult != null && "1".equals(existResult.getPushStatus().toString())) {
                log.warn("检测结果已存在且已推送成功，样本编号: {}", requestDTO.getSampleId());
                // 根据业务需求决定：覆盖/忽略/报错
                // 此处选择更新（保留最新结果）
            }

            // 3. 转换并保存结果
            TestResultInfo resultInfo = pushResultUtil.convertToEntity(requestDTO);
            //
            //后续操作均为假设
            //
            resultInfo.setPushStatus(1); // 1-推送成功
            resultInfo.setOldSampleNum(sampleInfo.getOldSampleNum()); // 确保冗余字段一致

            // 4. 更新样本状态为"已完成"
            sampleInfo.setStatus(2); // 2-已完成
            sampleMapper.updateById(sampleInfo);

            // 5. 保存/更新结果
            if (existResult != null) {
                resultInfo.setId(existResult.getId());
                resultInfoMapper.updateById(resultInfo);
                log.info("更新检测结果成功，ID: {}", resultInfo.getId());
            } else {
                resultInfoMapper.insert(resultInfo);
                log.info("新增检测结果成功，ID: {}", resultInfo.getId());
            }

            return true;

        } catch (BusinessException e) {
            log.error("业务异常，样本编号: {}, 原因: {}", requestDTO.getSampleId(), e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("系统异常，样本编号: {}", requestDTO.getSampleId(), e);
            throw new BusinessException("200909149", "结果保存失败: " + e.getMessage());
        }
    }
}
