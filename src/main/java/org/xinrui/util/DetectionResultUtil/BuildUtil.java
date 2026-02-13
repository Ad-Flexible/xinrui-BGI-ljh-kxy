package org.xinrui.util;

import org.xinrui.dto.detectionresult.DetectionResultDto;
import org.xinrui.dto.testresult.TestResultDto;
import org.xinrui.dto.testresult.nested.TestCnvDto;
import org.xinrui.entity.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



public class BuildUtil {

    private static final Long UPDATED_BY = 1L; // 固定更新人ID
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
        info.setPatientName(dto.getPatientName());
        info.setPatientAge(dto.getPatientAge());
        info.setPatientSex(dto.getPatientSex());
        info.setPatientPhone(dto.getPatientPhone());
        info.setPatientAddress(dto.getPatientAddress());
        info.setPatientRemark(dto.getPatientRemark());
        info.setUpdatedBy(UPDATED_BY);
        info.setUpdatedOn(LocalDateTime.now());
        return info;   //待完善
    }

    public static ExaminationInfo buildExaminationInfo(DetectionResultDto dto, SampleInfo sampleInfo) {
        ExaminationInfo exam = new ExaminationInfo();
        exam.setSampleOid(sampleInfo.getOid());
        exam.setPatientAge(dto.getPatientAge());
        exam.setFetusType(convertFetusType(dto.getFetusType()));
        exam.setGestationalWeeks(convertGestationalWeeks(dto.getGestationalWeeks()));
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
        exam.setUpdatedBy(UPDATED_BY);
        exam.setUpdatedOn(LocalDateTime.now());
        return exam;
    }

    public static SampleQcInfo buildSampleQcInfo(DetectionResultDto dto, SampleInfo sampleInfo) {
        SampleQcInfo qc = new SampleQcInfo();
        qc.setSampleOid(sampleInfo.getOid());
        qc.setSampleQcResult(dto.getSampleQcResult());
        qc.setSampleGc(convertBigDecimal(dto.getSampleQc().getSampleGc()));
        qc.setUniqueReads(convertBigDecimal(dto.getSampleQc().getUniqueReads()));
        qc.setReadsNum(convertBigDecimal(dto.getSampleQc().getReadsNum()));
        qc.setDuplicationRate(convertBigDecimal(dto.getSampleQc().getDuplicationRate()));
        qc.setMapRate(convertBigDecimal(dto.getSampleQc().getMapRate()));
        qc.setUpdatedBy(UPDATED_BY);
        qc.setUpdatedOn(LocalDateTime.now());
        return qc;
    }

    public static LaneQcInfo buildLaneQcInfo(DetectionResultDto dto, SampleInfo sampleInfo) {
        LaneQcInfo qc = new LaneQcInfo();
        qc.setSampleOid(sampleInfo.getOid());
        qc.setLaneQcResult(dto.getLaneQcResult());
        qc.setLaneQcReason(dto.getLaneQcReason());
        qc.setLaneReadsMean(convertBigDecimal(dto.getLaneQc().getReadsNum()));
        qc.setLaneMapRate(convertBigDecimal(dto.getLaneQc().getMapRate()));
        qc.setTotalDecodeRate(convertBigDecimal(dto.getLaneQc().getTotalDecodeRate()));
        qc.setLaneDuplicateRate(convertBigDecimal(dto.getLaneQc().getDuplicateRate()));
        qc.setFailSampleRate(convertBigDecimal(dto.getLaneQc().getFailSampleRate()));
        qc.setLaneGcMean(convertBigDecimal(dto.getLaneQc().getLaneGc()));
        qc.setTotalReads(convertBigDecimal(dto.getLaneQc().getTotalReads()));
        qc.setQ20(convertBigDecimal(dto.getLaneQc().getQ20()));
        qc.setDimRate(convertBigDecimal(dto.getLaneQc().getDimRate()));
        qc.setUpdatedBy(UPDATED_BY);
        qc.setUpdatedOn(LocalDateTime.now());
        return qc;
    }

    public static TestResultInfo buildTestResultInfo(DetectionResultDto dto, SampleInfo sampleInfo) {
        TestResultInfo result = new TestResultInfo();
        result.setSampleOid(sampleInfo.getOid());
        result.setTestTime(convertDateTime(dto.getTestTime()));
        result.setTestResult(dto.getDetectionResult());
        result.setReportType(dto.getReportType());
        result.setReportCreateTime(convertDateTime(dto.getReportCreateTime()));
        result.setDownScreeningResult(convertDownScreening(dto.getDownScreening()));
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
        result.setRecommendedOperation(dto.getRecommendedOperation());
        result.setDoctorOpinion(dto.getDoctorOpinion());
        result.setUpdatedBy(UPDATED_BY);
        result.setUpdatedOn(LocalDateTime.now());
        return result;
    }


}