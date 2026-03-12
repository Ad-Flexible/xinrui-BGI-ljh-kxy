package org.xinrui.service;

import org.xinrui.dto.SampleDto;
import org.xinrui.dto.SampleRegistrationDto;
import org.xinrui.entity.PatientInfo;
import org.xinrui.entity.SampleInfo;

/**
 * RequestSampleService接口
 * 这是一个服务接口，可能用于Halos请求样本信息相关的业务逻辑
 */
public interface SampleService {

    /**
     * 根据原样本编号查询样本信息
     * @param oldSampleNum 样本编号
     * @return 样本信息DTO
     * @throws org.xinrui.exception.BusinessException 业务异常
     */
    SampleDto getSample(String oldSampleNum);

    boolean handleSampleRegistrationInfo(SampleRegistrationDto sampleRegistrationDto);

    PatientInfo handlePatientInfo(SampleRegistrationDto sampleRegistrationDto);

    SampleInfo handleSampleInfo(SampleRegistrationDto sampleRegistrationDto,Long patientOid);

    void handleExaminationInfo(SampleRegistrationDto sampleRegistrationDto, Long sampleOid);


    /**
     * 根据筛查档案 ID 获取样本登记信息
     * @param screeningArchivesId 筛查档案 ID
     * @return 样本登记 DTO
     */
    SampleRegistrationDto getSampleRegistrationBySAId(Long screeningArchivesId);

}
