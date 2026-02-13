package org.xinrui.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xinrui.dto.testresult.TestResultDto;
import org.xinrui.entity.*;
import org.xinrui.exception.BusinessException;
import org.xinrui.mapper.*;
import org.xinrui.service.DetectionResultService;
import org.xinrui.service.TestResultService;
import org.xinrui.util.BuildUtil;
import org.xinrui.util.UpdateUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class TestResultServiceImpl implements TestResultService {

    @Autowired
    private SampleInfoMapper sampleInfoMapper;

    @Autowired
    private PatientInfoMapper patientInfoMapper;

    @Autowired
    private ExaminationInfoMapper examinationInfoMapper;

    @Autowired
    private SampleQcInfoMapper sampleQcInfoMapper;

    @Autowired
    private LaneQcInfoMapper laneQcInfoMapper;

    @Autowired
    private TestResultInfoMapper testResultInfoMapper;

    @Autowired
    private TestCnvInfoMapper testCnvInfoMapper;

    private static final Long UPDATED_BY = 1L; // 固定更新人ID

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean handlePushResult(DetectionResultDto requestDTO) {
        log.info("开始处理Halos推送结果，样本编号: {}", requestDTO.getSampleId());

        // 1. 处理样本信息（t_lis_sample）
        SampleInfo sampleInfo = handleSampleInfo(requestDTO);

        // 2. 处理患者信息（t_mchi_patient）
        handlePatientInfo(requestDTO, sampleInfo);

        // 3. 处理检查信息（t_lis_examination）
        handleExaminationInfo(requestDTO, sampleInfo);

        // 4. 处理样本质控（t_lis_sample_qc）
        handleSampleQcInfo(requestDTO, sampleInfo);

        // 5. 处理Lane质控（t_lis_lane_qc）
        handleLaneQcInfo(requestDTO, sampleInfo);

        // 6. 处理检测结果（t_lis_test_result）
        Long testResultOid = handleTestResultInfo(requestDTO, sampleInfo);

        // 7. 处理CNV信息（t_lis_test_cnv）
        handleTestCnvInfo(requestDTO, testResultOid);

        log.info("Halos推送结果处理成功，样本编号: {}", requestDTO.getSampleId());
        return true;
    }

    // ==================== 核心处理方法 ====================

    private SampleInfo handleSampleInfo(DetectionResultDto dto) {
        // 通过sample_id和old_sample_num查询
        SampleInfo sampleInfo = sampleInfoMapper.selectOne(
                Wrappers.<SampleInfo>lambdaQuery()
                        .eq(SampleInfo::getSampleId, dto.getSampleId())
                        .eq(SampleInfo::getOldSampleNum, dto.getOldSampleNum())
        );

        if (sampleInfo == null) {
            sampleInfo = BuildUtil.buildSampleInfo(dto);
            sampleInfoMapper.insert(sampleInfo);
        } else {
            UpdateUtil.updateSampleInfo(sampleInfo, dto);
            sampleInfoMapper.updateById(sampleInfo);
        }
        return sampleInfo;
    }

    private void handlePatientInfo(DetectionResultDto dto, SampleInfo sampleInfo) {
        // 通过身份证号查询患者
        PatientInfo patientInfo = patientInfoMapper.selectOne(
                Wrappers.<PatientInfo>lambdaQuery()
                        .eq(PatientInfo::getPatientIdCard, dto.getPatientIdCard())
        );

        if (patientInfo == null) {
            // 插入新患者
            patientInfo = BuildUtil.buildPatientInfo(dto);
            patientInfoMapper.insert(patientInfo);
            sampleInfo.setPatientOid(patientInfo.getOid());
        } else {
            // 更新现有患者
            UpdateUtil.updatePatientInfo(patientInfo, dto);
            patientInfoMapper.updateById(patientInfo);
            sampleInfo.setPatientOid(patientInfo.getOid());
        }
        sampleInfoMapper.updateById(sampleInfo); // 更新样本关联患者ID
    }

    private void handleExaminationInfo(DetectionResultDto dto, SampleInfo sampleInfo) {
        // 通过sample_oid查询检查信息
        ExaminationInfo exam = examinationInfoMapper.selectOne(
                Wrappers.<ExaminationInfo>lambdaQuery()
                        .eq(ExaminationInfo::getSampleOid, sampleInfo.getOid())
        );

        if (exam == null) {
            exam = BuildUtil.buildExaminationInfo(dto, sampleInfo);
            examinationInfoMapper.insert(exam);
        } else {
            UpdateUtil.updateExaminationInfo(exam, dto);
            examinationInfoMapper.updateById(exam);
        }
    }

    private void handleSampleQcInfo(DetectionResultDto dto, SampleInfo sampleInfo) {
        if (dto.getSampleQc() == null) return;

        // 通过sample_oid查询样本质控
        SampleQcInfo qc = sampleQcInfoMapper.selectOne(
                Wrappers.<SampleQcInfo>lambdaQuery()
                        .eq(SampleQcInfo::getSampleOid, sampleInfo.getOid())
        );

        if (qc == null) {
            qc = BuildUtil.buildSampleQcInfo(dto, sampleInfo);
            sampleQcInfoMapper.insert(qc);
        } else {
            UpdateUtil.updateSampleQcInfo(qc, dto);
            sampleQcInfoMapper.updateById(qc);
        }
    }

    private void handleLaneQcInfo(DetectionResultDto dto, SampleInfo sampleInfo) {
        if (dto.getLaneQc() == null) return;

        // 通过sample_oid查询Lane质控
        LaneQcInfo qc = laneQcInfoMapper.selectOne(
                Wrappers.<LaneQcInfo>lambdaQuery()
                        .eq(LaneQcInfo::getSampleOid, sampleInfo.getOid())
        );

        if (qc == null) {
            qc = BuildUtil.buildLaneQcInfo(dto, sampleInfo);
            laneQcInfoMapper.insert(qc);
        } else {
            UpdateUtil.updateLaneQcInfo(qc, dto);
            laneQcInfoMapper.updateById(qc);
        }
    }

    private Long handleTestResultInfo(DetectionResultDto dto, SampleInfo sampleInfo) {
        // 通过sample_oid查询检测结果
        TestResultInfo result = testResultInfoMapper.selectOne(
                Wrappers.<TestResultInfo>lambdaQuery()
                        .eq(TestResultInfo::getSampleOid, sampleInfo.getOid())
        );

        if (result == null) {
            result = BuildUtil.buildTestResultInfo(dto, sampleInfo);
            testResultInfoMapper.insert(result);
        } else {
            UpdateUtil.updateTestResultInfo(result, dto);
            testResultInfoMapper.updateById(result);
        }
        return result.getOid();
    }

    private void handleTestCnvInfo(DetectionResultDto dto, Long testResultOid) {
        // CNV信息无需查询，直接批量插入
        if (dto.getDiseaseList() != null) {
            List<TestCnvInfo> cnvList = dto.getDiseaseList().stream()
                    .map(detectionCnv -> {
                        TestCnvInfo info = new TestCnvInfo();
                        info.setResultOid(testResultOid);
                        info.setCnvCategory("D");
                        info.setCytoband(detectionCnv.getDiseaseInfo().getCytoband());
                        info.setChrNum(detectionCnv.getDiseaseInfo().getChr());
                        info.setCnvType(detectionCnv.getDiseaseInfo().getCnvType());
                        info.setCnvSize(detectionCnv.getDiseaseInfo().getCnvSize());
                        info.setSite(detectionCnv.getDiseaseInfo().getSite());
                        info.setDelDupDesc(detectionCnv.getDiseaseInfo().getDisease());
                        info.setDiseaseName(detectionCnv.getDiseaseInfo().getDisease());
                        info.setDiseaseDetail(detectionCnv.getDiseaseInfo().getDiseaseDetail());
                        info.setDiseaseDescription(detectionCnv.getDiseaseInfo().getDetail());
                        info.setUpdatedBy(UPDATED_BY);
                        info.setUpdatedOn(LocalDateTime.now());
                        return info;
                    })
                    .collect(Collectors.toList());
            testCnvInfoMapper.insertBatch(cnvList);
        }

        if (dto.getOtherDiseaseList() != null) {
            List<TestCnvInfo> cnvList = dto.getOtherDiseaseList().stream()
                    .map(diseaseInfo -> {
                        TestCnvInfo info = new TestCnvInfo();
                        info.setResultOid(testResultOid);
                        info.setCnvCategory("O");
                        info.setCytoband(diseaseInfo.getCytoband());
                        info.setChrNum(diseaseInfo.getChr());
                        info.setCnvType(diseaseInfo.getCnvType());
                        info.setCnvSize(diseaseInfo.getCnvSize());
                        info.setSite(diseaseInfo.getSite());
                        info.setDelDupDesc(diseaseInfo.getDisease());
                        info.setDiseaseName(diseaseInfo.getDisease());
                        info.setDiseaseDetail(diseaseInfo.getDiseaseDetail());
                        info.setDiseaseDescription(diseaseInfo.getDetail());
                        info.setUpdatedBy(UPDATED_BY);
                        info.setUpdatedOn(LocalDateTime.now());
                        return info;
                    })
                    .collect(Collectors.toList());
            testCnvInfoMapper.insertBatch(cnvList);
        }
    }
}