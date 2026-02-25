package org.xinrui.service;

import org.xinrui.dto.testResult.TestResultDto;
import org.xinrui.entity.PatientInfo;
import org.xinrui.entity.SampleInfo;

/**
 * Halos推送结果数据服务接口
 * 该接口定义了处理推送结果相关的方法
 */
public interface TestResultService {

    /**
     * 处理Halos推送的检测结果
     * @param requestDTO 推送数据
     * @return 处理结果（true-成功 false-失败）
     */
    boolean handlePushResult(TestResultDto requestDTO);

    SampleInfo handleSampleInfo(TestResultDto dto, Long patientOid);

    PatientInfo handlePatientInfo(TestResultDto dto);

    void handleExaminationInfo(TestResultDto dto, SampleInfo sampleInfo);

    void handleSampleQcInfo(TestResultDto dto, SampleInfo sampleInfo);

    void handleLaneQcInfo(TestResultDto dto, SampleInfo sampleInfo);

    Long handleTestResultInfo(TestResultDto dto, SampleInfo sampleInfo);

    void handleTestCnvInfo(TestResultDto dto, Long testResultOid);
}
