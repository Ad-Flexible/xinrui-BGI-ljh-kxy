package org.xinrui.util;

import org.xinrui.dto.detectionresult.DetectionResultDto;
import org.xinrui.entity.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class UpdateUtil {

    public static void updateSampleInfo(SampleInfo sampleInfo, DetectionResultDto dto) {
        if (dto.getPoolingSubId() != null) sampleInfo.setPoolingSubId(dto.getPoolingSubId());
        if (dto.getSlideId() != null) sampleInfo.setSlideId(dto.getSlideId());
        if (dto.getLaneId() != null) sampleInfo.setLaneId(dto.getLaneId());
        if (dto.getDnbId() != null) sampleInfo.setDnbId(dto.getDnbId());
        if (dto.getSampleType() != null) sampleInfo.setSampleType(convertSampleType(dto.getSampleType()));
        if (dto.getShipmentCondition() != null)
            sampleInfo.setShipmentCondition(convertShipmentCondition(dto.getShipmentCondition()));
        if (dto.getTubeType() != null) sampleInfo.setTubeType(convertTubeType(dto.getTubeType()));
        if (dto.getCollectDate() != null)
            sampleInfo.setCollectDate(convertDateTime(dto.getCollectDate()));
        if (dto.getReceivedDate() != null)
            sampleInfo.setReceivedDate(convertDateTime(dto.getReceivedDate()));
        if (dto.getGestationalWeeks() != null)
            sampleInfo.setGestationalWeeks(convertGestationalWeeks(dto.getGestationalWeeks()));
        if (dto.getFetusType() != null)
            sampleInfo.setFetusType(convertFetusType(dto.getFetusType()));
        if (dto.getAdditionalReportFlag() != 0)
            sampleInfo.setAdditionalReportFlag(dto.getAdditionalReportFlag());
        if (dto.getOldSampleNum() != null) sampleInfo.setOldSampleNum(dto.getOldSampleNum());
        if (dto.getRepeatCount() != 0) sampleInfo.setRepeatCount(dto.getRepeatCount());

        sampleInfo.setUpdatedBy(UPDATED_BY);
        sampleInfo.setUpdatedOn(LocalDateTime.now());
    }

    public static void updatePatientInfo(PatientInfo patientInfo, DetectionResultDto dto) {
        if (dto.getPatientName() != null) patientInfo.setPatientName(dto.getPatientName());
        if (dto.getPatientId() != null) patientInfo.setPatientId(dto.getPatientId());
        if (dto.getPatientPhone() != null) patientInfo.setPatientPhone(dto.getPatientPhone());
        if (dto.getPatientAddress() != null) patientInfo.setPatientAddress(dto.getPatientAddress());
        if (dto.getPatientEmail() != null) patientInfo.setPatientEmail(dto.getPatientEmail());
        if (dto.getPatientAge() != null) patientInfo.setPatientAge(dto.getPatientAge());

    }

    public static void updateExaminationInfo(ExaminationInfo exam, DetectionResultDto dto) {
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
    }

    // 其他更新方法（SampleQc, LaneQc, TestResult）类似，此处省略
    public static void updateSampleQcInfo(SampleQcInfo qc, DetectionResultDto dto) {
        // 实现与updateSampleInfo类似
    }

    public static void updateLaneQcInfo(LaneQcInfo qc, DetectionResultDto dto) {
        // 实现与updateSampleInfo类似
    }

    public static void updateTestResultInfo(TestResultInfo result, DetectionResultDto dto) {
        // 实现与updateSampleInfo类似
    }

    // 保持原有转换方法（与原util相同）
    private static Integer convertSampleType(String type) { /* ... */ }
    // ... 其他转换方法保持不变
}