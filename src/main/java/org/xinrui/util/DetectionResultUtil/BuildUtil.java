package org.xinrui.util;

import org.xinrui.dto.detectionresult.DetectionResultDto;
import org.xinrui.dto.testresult.TestResultDto;
import org.xinrui.dto.testresult.nested.LaneQcDto;
import org.xinrui.dto.testresult.nested.SampleQcDto;
import org.xinrui.dto.testresult.nested.TestCnvDto;
import org.xinrui.entity.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



public class BuildUtil {

    private static final Long UPDATED_BY = 1L; // 固定更新人ID
    private static final Long CREATE_BY = 2L; // 固定创建人ID
    private static final int INVERT = 1; //固定更新版本值为1

    public static SampleInfo buildSampleInfo(TestResultDto dto) {
        SampleInfo info = new SampleInfo();
        info.setSampleId(dto.getSampleId());
        info.setPoolingSubId(dto.getPoolingSubId());
        info.setSlideId(dto.getSlideId());
        info.setLaneId(dto.getLaneId());
        info.setDnbId(dto.getDnbId());
        info.setSampleType(convertSampleType(dto.getSampleType()));
        info.setShipmentCondition(convertShipmentCondition(dto.getShipmentCondition()));
        info.setTubeType(convertTubeType(dto.getTubeType()));
        info.setCollectDate(convertDateTime(dto.getCollectDate()));
        info.setReceivedDate(convertDateTime(dto.getReceivedDate()));
        info.setAdditionalReportFlag(dto.getAdditionalReportFlag());
        info.setOldSampleNum(dto.getOldSampleNum());
        info.setRepeatCount(dto.getRepeatCount());
        // 设置缺失字段 - 从diseaseList获取产品信息
        if (dto.getDiseaseList() != null && !dto.getDiseaseList().isEmpty()) {
            TestCnvDto firstTestCnv = dto.getDiseaseList().get(0);
            info.setProductNo(firstTestCnv.getProductNo());
            info.setProductName(firstTestCnv.getProductName());
        } else {
            // 如果diseaseList为空，可以设置为默认值
            info.setProductNo(null);
            info.setProductName(null);
        }
        info.setClinicNum(dto.getClinicNum());
        info.setHospitalName(dto.getHospitalName());
        info.setDepartmentName(null);
        info.setDoctorName(dto.getDoctorName());
        info.setIntver(INVERT);
        info.setUpdatedBy(UPDATED_BY);
        info.setUpdatedOn(LocalDateTime.now());
        return info;
    }

    public static PatientInfo buildPatientInfo(TestResultDto dto) {
        PatientInfo info = new PatientInfo();
        info.setName(dto.getPatientName());
        info.setPhone(dto.getPatientMobile());
        info.setPatientTel(null);
        info.setSex("女");  //因为是孕检相关所以统一设置为女
        info.setBirthday(null);
        info.setIdentity(dto.getPatientIdCard());
        info.setIncome(null);
        info.setPermanentAddress(null);
        info.setPermanentAddressDetail(null);
        info.setCurrentAddress(null);
        info.setCurrentAddressDetail(dto.getPatientAddress());
        info.setEmergencyContact(null); // 紧急联系人
        info.setEmergentRelation(null); // 与紧急联系人关系
        info.setEmergencyContactPhone(null); // 紧急联系人电话
        info.setEnable(1); // 默认有效
        info.setIntver(INVERT); // 版本号初始为0
        info.setCreatedBy(UPDATED_BY); // 创建人
        info.setCreatedOn(LocalDateTime.now()); // 创建时间
        info.setUpdatedBy(UPDATED_BY); // 更新人
        info.setUpdatedOn(LocalDateTime.now()); // 更新时间
        return info;   //待完善
    }

    public static ExaminationInfo buildExaminationInfo(TestResultDto dto, SampleInfo sampleInfo) {
        ExaminationInfo exam = new ExaminationInfo();
        exam.setSampleOid(sampleInfo.getOid());

        // 设置患者年龄
        exam.setPatientAge(dto.getPatientAge());
        exam.setPatientHeight(null);
        exam.setPatientWeight(null);
        exam.setPatientEdd(null);

        // 处理孕周（TestResultDto中的gestationalWeeks是"周,天"格式的字符串）
        if (dto.getGestationalWeeks() != null && dto.getGestationalWeeks().contains(",")) {
            String[] weeksAndDays = dto.getGestationalWeeks().split(",");
            if (weeksAndDays.length >= 1) {
                exam.setGestationalWeeks(Integer.parseInt(weeksAndDays[0].trim()));
            }
            if (weeksAndDays.length >= 2) {
                exam.setGestationalDays(Integer.parseInt(weeksAndDays[1].trim()));
            }
        }

        // 转换其他字段
        exam.setFetusType(convertFetusType(dto.getFetusType()));
        exam.setLastMenstrualPeriod(convertDate(dto.getLastMenstrualPeriod()));
        exam.setChorionType(convertChorion(dto.getChorion()));
        exam.setBUltrasonography(convertBUltrasonography(dto.getBUltrasonography()));
        exam.setIvfFlag(convertIvfFlag(dto.getIvfFlag()));
        exam.setConceptionMethod(convertConceptionMethod(dto.getConceptionMethod()));
        exam.setBhGravidity(dto.getBhGravidity());
        exam.setBhParity(dto.getBhParity());
        exam.setBhOther(dto.getBhOther());
        exam.setAmniocentesisResult(dto.getAmniocentesis());
        exam.setIllnessHistoryPast(dto.getIllnessHistoryPast());
        exam.setIllnessHistoryPresent(dto.getIllnessHistoryPresent());
        exam.setIllnessHistoryAllergy(dto.getIllnessHistoryAllergy());
        exam.setIllnessHistoryGenetic(dto.getIllnessHistoryGenetic());
        exam.setPatientRemark(dto.getPatientRemark());

        // 设置审计字段
        exam.setIntver(INVERT);
        exam.setUpdatedBy(UPDATED_BY);
        exam.setUpdatedOn(LocalDateTime.now());


        return exam;
    }

    public static SampleQcInfo buildSampleQcInfo(TestResultDto dto, SampleInfo sampleInfo) {
        SampleQcInfo qc = new SampleQcInfo();
        qc.setSampleOid(sampleInfo.getOid());

        // 设置样本质控结果
        qc.setSampleQcResult(dto.getSampleQcResult());

        // 设置样本质控详细指标
        if (dto.getSampleQc() != null) {
            SampleQcDto sampleQcDto = dto.getSampleQc();
            qc.setSampleGc(convertBigDecimal(sampleQcDto.getSampleGc()));
            qc.setUniqueReads(convertBigDecimal(sampleQcDto.getUniqueReads()));
            qc.setReadsNum(convertBigDecimal(sampleQcDto.getReadsNum()));
            qc.setDuplicationRate(convertBigDecimal(sampleQcDto.getDuplicationRate()));
            qc.setMapRate(convertBigDecimal(sampleQcDto.getMapRate()));
        }

        // 设置审计字段
        qc.setIntver(INVERT);
        qc.setUpdatedBy(UPDATED_BY);
        qc.setUpdatedOn(LocalDateTime.now());

        return qc;
    }

    public static LaneQcInfo buildLaneQcInfo(TestResultDto dto, SampleInfo sampleInfo) {
        LaneQcInfo qc = new LaneQcInfo();
        qc.setSampleOid(sampleInfo.getOid());

        // 设置lane质控结果
        qc.setLaneQcResult(dto.getLaneQcResult());
        qc.setLaneQcReason(dto.getLaneQcReason());

        // 设置lane质控详细指标
        if (dto.getLaneQc() != null) {
            LaneQcDto laneQcDto = dto.getLaneQc();
            qc.setLaneReadsMean(convertBigDecimal(laneQcDto.getReadsNum()));
            qc.setLaneMapRate(convertBigDecimal(laneQcDto.getMapRate()));
            qc.setTotalDecodeRate(convertBigDecimal(laneQcDto.getTotalDecodeRate()));
            qc.setLaneDuplicateRate(convertBigDecimal(laneQcDto.getDuplicateRate()));
            qc.setFailSampleRate(convertBigDecimal(laneQcDto.getFailSampleRate()));
            qc.setLaneGcMean(convertBigDecimal(laneQcDto.getLaneGc()));
            qc.setTotalReads(convertBigDecimal(laneQcDto.getTotalReads()));
            qc.setQ20(convertBigDecimal(laneQcDto.getQ20()));
            qc.setDimRate(convertBigDecimal(laneQcDto.getDimRate()));
        }

        // 设置审计字段
        qc.setIntver(INVERT);
        qc.setUpdatedBy(UPDATED_BY);
        qc.setUpdatedOn(LocalDateTime.now());

        return qc;

    }

    public static TestResultInfo buildTestResultInfo(TestResultDto dto, SampleInfo sampleInfo) {
            TestResultInfo result = new TestResultInfo();
            result.setSampleOid(sampleInfo.getOid());

            // 设置检测基本信息
            result.setTestTime(convertDateTime(dto.getTestTime()));
            result.setTestResult(dto.getDetectionResult());
            result.setReportType(dto.getReportType());
            result.setReportCreateTime(convertDateTime(dto.getReportCreateTime()));
            result.setDownScreeningResult(convertDownScreening(dto.getDownScreening()));

            // 设置染色体相关数据
            result.setZChr13(convertBigDecimal(dto.getZChr13()));
            result.setZChr18(convertBigDecimal(dto.getZChr18()));
            result.setZChr21(convertBigDecimal(dto.getZChr21()));
            result.setTestChr13(dto.getTestChr13());
            result.setTestChr18(dto.getTestChr18());
            result.setTestChr21(dto.getTestChr21());
            result.setTestChrSex(dto.getTestChrSex());
            result.setAbnormalChrX(dto.getAbnormalChrX());
            result.setFracAbs(convertBigDecimal(dto.getFracAbs()));
            result.setFetalFaction(convertBigDecimal(dto.getFetalFraction()));
            result.setChildLvChr13(convertBigDecimal(dto.getChildLvChr13()));
            result.setChildLvChr18(convertBigDecimal(dto.getChildLvChr18()));
            result.setChildLvChr21(convertBigDecimal(dto.getChildLvChr21()));
            result.setRiskIndex13(dto.getRiskIndex13());
            result.setRiskIndex18(dto.getRiskIndex18());
            result.setRiskIndex21(dto.getRiskIndex21());
            result.setTestDeletionDuplication(dto.getTestDeletionDuplication());
            result.setTestChrOther(dto.getTestChrOther());
            result.setAbnormalChrNum(dto.getAbnormalChrNum());
            result.setExtraInformation(dto.getChrN());
            result.setRecommendedOperation(dto.getRecommendedOperation());
            result.setDoctorOpinion(dto.getDoctorOpinion());

            // 设置审计字段
            result.setIntver(INVERT);
            result.setUpdatedBy(UPDATED_BY);
            result.setUpdatedOn(LocalDateTime.now());

            return result;
    }


}