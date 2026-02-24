package org.xinrui.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xinrui.dto.testResult.TestResultDto;
import org.xinrui.dto.testResult.nested.DiseaseDto;
import org.xinrui.entity.*;
import org.xinrui.exception.BusinessException;
import org.xinrui.mapper.*;
import org.xinrui.util.testResult.*;
import org.xinrui.service.TestResultService;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class TestResultServiceImpl implements TestResultService {

    private static final Long UPDATED_BY = 1L; // 固定更新人ID

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



    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean handlePushResult(TestResultDto requestDTO) {



        log.info("开始处理Halos推送结果，样本编号: {}", requestDTO.getSampleId());

        // 1. 处理患者信息（t_mchi_patient）
        PatientInfo patientInfo = handlePatientInfo(requestDTO);

        // 2. 处理样本信息（t_lis_sample）
        SampleInfo sampleInfo = handleSampleInfo(requestDTO, patientInfo.getOid());

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

    private SampleInfo handleSampleInfo(TestResultDto dto, Long patientOid) {
        // 通过sample_id和old_sample_num查询
        SampleInfo sampleInfo = sampleInfoMapper.selectOne(
                Wrappers.<SampleInfo>lambdaQuery()
                        .eq(SampleInfo::getSampleId, dto.getSampleId())
                        .eq(SampleInfo::getOldSampleNum, dto.getOldSampleNum())
        );

        if (sampleInfo == null) {
            sampleInfo = BuildUtil.buildSampleInfo(dto);
            sampleInfo.setPatientOid(patientOid);
            sampleInfoMapper.insert(sampleInfo);
        } else {
            UpdateUtil.updateSampleInfo(sampleInfo, dto);
            sampleInfo.setPatientOid(patientOid);
            sampleInfoMapper.updateById(sampleInfo);
        }

        // 由于oid为自增主键，为了得到完整的sampleInfo只能从数据库中重新查询
        return sampleInfoMapper.selectOne(
                Wrappers.<SampleInfo>lambdaQuery()
                        .eq(SampleInfo::getSampleId, dto.getSampleId())
                        .eq(SampleInfo::getOldSampleNum, dto.getOldSampleNum())
        );
    }

    private PatientInfo handlePatientInfo(TestResultDto dto) {
        // 通过身份证号查询患者
        PatientInfo patientInfo = patientInfoMapper.selectOne(
                Wrappers.<PatientInfo>lambdaQuery()
                        .eq(PatientInfo::getIdentity, dto.getPatientIdCard())
        );

        if (patientInfo == null) {
            // 插入新患者
            patientInfo = BuildUtil.buildPatientInfo(dto);
            patientInfoMapper.insert(patientInfo);
        } else {
            // 更新现有患者
            UpdateUtil.updatePatientInfo(patientInfo, dto);
            patientInfoMapper.updateById(patientInfo);
        }
        return patientInfo;
    }

    private void handleExaminationInfo(TestResultDto dto, SampleInfo sampleInfo) {
        if(sampleInfo == null){
            throw new BusinessException("样本信息为空");
        }
        // 通过sample_oid查询检查信息
        ExaminationInfo exam = examinationInfoMapper.selectOne(
                Wrappers.<ExaminationInfo>lambdaQuery()
                        .eq(ExaminationInfo::getSampleOid, sampleInfo.getOid())
        );

        if (exam == null) {
            exam = BuildUtil.buildExaminationInfo(dto, sampleInfo);
//            log.info("exam:{}", exam);//测试使用
            examinationInfoMapper.insert(exam);
        } else {
            UpdateUtil.updateExaminationInfo(exam, dto);
//            log.info("exam:{}", exam);//测试使用
            examinationInfoMapper.updateById(exam);
        }
    }

    private void handleSampleQcInfo(TestResultDto dto, SampleInfo sampleInfo) {
        if(sampleInfo == null){
            throw new BusinessException("样本信息为空");
        }

        if (dto.getSampleQc() == null) {
            log.info("SampleQcInfo为空，跳过处理");
            return;
        }

        // 通过sample_oid查询样本质控
        SampleQcInfo qc = sampleQcInfoMapper.selectOne(
                Wrappers.<SampleQcInfo>lambdaQuery()
                        .eq(SampleQcInfo::getSampleOid, sampleInfo.getOid())
        );

        if (qc == null) {
            qc = BuildUtil.buildSampleQcInfo(dto, sampleInfo);
//            log.info("qc:{}", qc);//测试使用
            sampleQcInfoMapper.insert(qc);
        } else {
            UpdateUtil.updateSampleQcInfo(qc, dto);
//            log.info("qc:{}", qc);//测试使用
            sampleQcInfoMapper.updateById(qc);
        }
    }

    private void handleLaneQcInfo(TestResultDto dto, SampleInfo sampleInfo) {

        if(sampleInfo == null){
            throw new BusinessException("样本信息为空");
        }

        if (dto.getLaneQc() == null) {
            log.info("LaneQcInfo为空，跳过处理");
            return;
        }

        // 通过sample_oid查询Lane质控
        LaneQcInfo qc = laneQcInfoMapper.selectOne(
                Wrappers.<LaneQcInfo>lambdaQuery()
                        .eq(LaneQcInfo::getSampleOid, sampleInfo.getOid())
        );

        if (qc == null) {
            qc = BuildUtil.buildLaneQcInfo(dto, sampleInfo);
//            log.info("qc:{}", qc);//测试使用
            laneQcInfoMapper.insert(qc);
        } else {
            UpdateUtil.updateLaneQcInfo(qc, dto);
//            log.info("qc:{}", qc);//测试使用
            laneQcInfoMapper.updateById(qc);
        }
    }

    private Long handleTestResultInfo(TestResultDto dto, SampleInfo sampleInfo) {

        if(sampleInfo == null){
            throw new BusinessException("样本信息为空");
        }

        // 通过sample_oid查询检测结果
        TestResultInfo result = testResultInfoMapper.selectOne(
                Wrappers.<TestResultInfo>lambdaQuery()
                        .eq(TestResultInfo::getSampleOid, sampleInfo.getOid())
        );

        if (result == null) {
            result = BuildUtil.buildTestResultInfo(dto, sampleInfo);
//            log.info("result:{}", result);//测试使用
            testResultInfoMapper.insert(result);
        } else {
            UpdateUtil.updateTestResultInfo(result, dto);
//            log.info("result:{}", result);//测试使用
            testResultInfoMapper.updateById(result);
        }
        TestResultInfo resultInfo = testResultInfoMapper.selectOne(
                Wrappers.<TestResultInfo>lambdaQuery()
                        .eq(TestResultInfo::getSampleOid, sampleInfo.getOid())
        );
        return resultInfo.getOid();
    }

    private void handleTestCnvInfo(TestResultDto dto, Long testResultOid) {
        // CNV信息无需查询，直接批量插入
        if (dto.getDiseaseList() != null) {
            List<TestCnvInfo> cnvList = dto.getDiseaseList().stream()
                    .map(testCnvDto -> {
                        TestCnvInfo info = new TestCnvInfo();
                        info.setResultOid(testResultOid);
                        info.setCnvCategory("D");
                        // 不确定逻辑要不要改为diseaseDto为null的话直接不做insert了
                        // 目前为null的话还是会插入审计字段和ResultOid以及CnvCategory
                        DiseaseDto diseaseDto = testCnvDto.getDiseaseDto();
                        if (diseaseDto != null) {
                            info.setCytoband(diseaseDto.getCytoband());
                            info.setChrNum(diseaseDto.getChr());
                            info.setCnvType(diseaseDto.getCnvType());
                            info.setCnvSize(ConvertUtil.convertBigDecimal(diseaseDto.getCnvSize()));
                            info.setSite(diseaseDto.getSite());
                            info.setDelDupDesc(diseaseDto.getDisease());
                            info.setDiseaseName(diseaseDto.getDisease());
                            info.setDiseaseDetail(diseaseDto.getDiseaseDetail());
                            info.setDiseaseDescription(diseaseDto.getDetail());
                        }

                        info.setUpdatedBy(UPDATED_BY);
                        info.setUpdatedOn(LocalDateTime.now());
                        return info;
                    })
                    .collect(Collectors.toList());
//            log.info("cnvList:{}",cnvList);//测试使用
            if(cnvList != null) testCnvInfoMapper.insertBatch(cnvList);
        }

        if (dto.getOtherDiseaseList() != null) {
            List<TestCnvInfo> cnvList = dto.getOtherDiseaseList().stream()
                    .map(diseaseDto -> {
                        TestCnvInfo info = new TestCnvInfo();
                        info.setResultOid(testResultOid);
                        info.setCnvCategory("O");
                        info.setCytoband(diseaseDto.getCytoband());
                        info.setChrNum(diseaseDto.getChr());
                        info.setCnvType(diseaseDto.getCnvType());
                        info.setCnvSize(ConvertUtil.convertBigDecimal(diseaseDto.getCnvSize()));
                        info.setSite(diseaseDto.getSite());
                        info.setDelDupDesc(diseaseDto.getDisease());
                        info.setDiseaseName(diseaseDto.getDisease());
                        info.setDiseaseDetail(diseaseDto.getDiseaseDetail());
                        info.setDiseaseDescription(diseaseDto.getDetail());
                        info.setUpdatedBy(UPDATED_BY);
                        info.setUpdatedOn(LocalDateTime.now());
                        return info;
                    })
                    .collect(Collectors.toList());
//            log.info("cnvList:{}",cnvList);//测试使用
            if(cnvList != null) testCnvInfoMapper.insertBatch(cnvList);
        }
    }

}