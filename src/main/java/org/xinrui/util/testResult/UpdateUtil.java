package org.xinrui.util.testResult;

import org.xinrui.dto.testresult.TestResultDto;
import org.xinrui.dto.testresult.nested.LaneQcDto;
import org.xinrui.dto.testresult.nested.SampleQcDto;
import org.xinrui.dto.testresult.nested.TestCnvDto;
import org.xinrui.entity.*;

import java.time.LocalDateTime;

public class UpdateUtil {

    private static final Long UPDATED_BY = 1L; // 固定更新人ID
    private static final int INVERT = 1; //固定更新版本值为1

    public static void updateSampleInfo(SampleInfo sampleInfo, TestResultDto dto) {
        if (dto.getPoolingSubId() != null) sampleInfo.setPoolingSubId(dto.getPoolingSubId());
        if (dto.getSlideId() != null) sampleInfo.setSlideId(dto.getSlideId());
        if (dto.getLaneId() != null) sampleInfo.setLaneId(dto.getLaneId());
        if (dto.getDnbId() != null) sampleInfo.setDnbId(dto.getDnbId());
        if (dto.getSampleType() != null)
            sampleInfo.setSampleType(ConvertUtil.convertSampleType(dto.getSampleType()));
        if (dto.getShipmentCondition() != null)
            sampleInfo.setShipmentCondition(ConvertUtil.convertShipmentCondition(dto.getShipmentCondition()));
        if (dto.getTubeType() != null)
            sampleInfo.setTubeType(ConvertUtil.convertTubeType(dto.getTubeType()));
        if (dto.getCollectDate() != null)
            sampleInfo.setCollectDate(ConvertUtil.convertDateTime(dto.getCollectDate()));
        if (dto.getReceivedDate() != null)
            sampleInfo.setReceivedDate(ConvertUtil.convertDateTime(dto.getReceivedDate()));
        if (dto.getAdditionalReportFlag() == 1 || dto.getAdditionalReportFlag() == 2 )
            sampleInfo.setAdditionalReportFlag(dto.getAdditionalReportFlag());
        if (dto.getOldSampleNum() != null)
            sampleInfo.setOldSampleNum(dto.getOldSampleNum());
        if (dto.getRepeatCount() != 0)
            sampleInfo.setRepeatCount(dto.getRepeatCount());

        // 处理产品信息 - 从diseaseList获取
        if (dto.getDiseaseList() != null && !dto.getDiseaseList().isEmpty()) {
            TestCnvDto firstTestCnv = dto.getDiseaseList().get(0);
            if (firstTestCnv.getProductNo() != null) {
                sampleInfo.setProductNo(firstTestCnv.getProductNo());
            }
            if (firstTestCnv.getProductName() != null) {
                sampleInfo.setProductName(firstTestCnv.getProductName());
            }
        }

        if (dto.getClinicNum() != null) sampleInfo.setClinicNum(dto.getClinicNum());
        if (dto.getHospitalName() != null) sampleInfo.setHospitalName(dto.getHospitalName());
        if (dto.getDoctorName() != null) sampleInfo.setDoctorName(dto.getDoctorName());

        sampleInfo.setUpdatedBy(UPDATED_BY);
        sampleInfo.setUpdatedOn(LocalDateTime.now());
    }

    public static void updatePatientInfo(PatientInfo patientInfo, TestResultDto dto) {
        if (dto.getPatientName() != null) patientInfo.setName(dto.getPatientName());
        if (dto.getPatientMobile() != null) patientInfo.setPhone(dto.getPatientMobile());
        if (dto.getPatientAddress() != null)
            patientInfo.setCurrentAddressDetail(dto.getPatientAddress());
        if (dto.getPatientIdCard() != null)
            patientInfo.setIdentity(dto.getPatientIdCard());

        patientInfo.setUpdatedBy(UPDATED_BY);
        patientInfo.setUpdatedOn(LocalDateTime.now());
    }

    public static void updateExaminationInfo(ExaminationInfo exam, TestResultDto dto) {
        if (dto.getPatientAge() >0 && dto.getPatientAge() < 100 )
            exam.setPatientAge(dto.getPatientAge());

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

        if (dto.getFetusType() != null)
            exam.setFetusType(ConvertUtil.convertFetusType(dto.getFetusType()));
        if (dto.getLastMenstrualPeriod() != null)
            exam.setLastMenstrualPeriod(ConvertUtil.convertDate(dto.getLastMenstrualPeriod()));
        if (dto.getChorion() != null)
            exam.setChorionType(ConvertUtil.convertChorion(dto.getChorion()));
        if (dto.getBUltrasonography() != null)
            exam.setBUltrasonography(ConvertUtil.convertBUltrasonography(dto.getBUltrasonography()));
        if (dto.getIvfFlag() != null)
            exam.setIvfFlag(ConvertUtil.convertIvfFlag(dto.getIvfFlag()));
        if (dto.getConceptionMethod() != null)
            exam.setConceptionMethod(ConvertUtil.convertConceptionMethod(dto.getConceptionMethod()));
        if (dto.getBhGravidity() >= 0)
            exam.setBhGravidity(dto.getBhGravidity());
        if (dto.getBhParity() >= 0)
            exam.setBhParity(dto.getBhParity());
        if (dto.getBhOther() != null)
            exam.setBhOther(dto.getBhOther());
        if (dto.getAmniocentesis() != null)
            exam.setAmniocentesisResult(dto.getAmniocentesis());
        if (dto.getIllnessHistoryPast() != null)
            exam.setIllnessHistoryPast(dto.getIllnessHistoryPast());
        if (dto.getIllnessHistoryPresent() != null)
            exam.setIllnessHistoryPresent(dto.getIllnessHistoryPresent());
        if (dto.getIllnessHistoryAllergy() != null)
            exam.setIllnessHistoryAllergy(dto.getIllnessHistoryAllergy());
        if (dto.getIllnessHistoryGenetic() != null)
            exam.setIllnessHistoryGenetic(dto.getIllnessHistoryGenetic());
        if (dto.getPatientRemark() != null)
            exam.setPatientRemark(dto.getPatientRemark());

        exam.setUpdatedBy(UPDATED_BY);
        exam.setUpdatedOn(LocalDateTime.now());
    }

    public static void updateSampleQcInfo(SampleQcInfo qc, TestResultDto dto) {
        if (dto.getSampleQcResult() != null)
            qc.setSampleQcResult(dto.getSampleQcResult());

        if (dto.getSampleQc() != null) {
            SampleQcDto sampleQcDto = dto.getSampleQc();
            if (sampleQcDto.getSampleGc() != null)
                qc.setSampleGc(ConvertUtil.convertBigDecimal(sampleQcDto.getSampleGc()));
            if (sampleQcDto.getUniqueReads() != null)
                qc.setUniqueReads(ConvertUtil.convertBigDecimal(sampleQcDto.getUniqueReads()));
            if (sampleQcDto.getReadsNum() != null)
                qc.setReadsNum(ConvertUtil.convertBigDecimal(sampleQcDto.getReadsNum()));
            if (sampleQcDto.getDuplicationRate() != null)
                qc.setDuplicationRate(ConvertUtil.convertBigDecimal(sampleQcDto.getDuplicationRate()));
            if (sampleQcDto.getMapRate() != null)
                qc.setMapRate(ConvertUtil.convertBigDecimal(sampleQcDto.getMapRate()));
        }

        qc.setUpdatedBy(UPDATED_BY);
        qc.setUpdatedOn(LocalDateTime.now());
    }

    public static void updateLaneQcInfo(LaneQcInfo qc, TestResultDto dto) {


        if (dto.getLaneQc() != null) {
            LaneQcDto laneQcDto = dto.getLaneQc();
            if (laneQcDto.getLaneQcResult() != null)
                qc.setLaneQcResult(laneQcDto.getLaneQcResult());
            if (laneQcDto.getLaneQcReason() != null)
                qc.setLaneQcReason(laneQcDto.getLaneQcReason());
            if (laneQcDto.getReadsNum() != null)
                qc.setLaneReadsMean(ConvertUtil.convertBigDecimal(laneQcDto.getReadsNum()));
            if (laneQcDto.getMapRate() != null)
                qc.setLaneMapRate(ConvertUtil.convertBigDecimal(laneQcDto.getMapRate()));
            if (laneQcDto.getTotalDecodeRate() != null)
                qc.setTotalDecodeRate(ConvertUtil.convertBigDecimal(laneQcDto.getTotalDecodeRate()));
            if (laneQcDto.getDuplicateRate() != null)
                qc.setLaneDuplicateRate(ConvertUtil.convertBigDecimal(laneQcDto.getDuplicateRate()));
            if (laneQcDto.getFailSampleRate() != null)
                qc.setFailSampleRate(ConvertUtil.convertBigDecimal(laneQcDto.getFailSampleRate()));
            if (laneQcDto.getLaneGc() != null)
                qc.setLaneGcMean(ConvertUtil.convertBigDecimal(laneQcDto.getLaneGc()));
            if (laneQcDto.getTotalReads() != null)
                qc.setTotalReads(ConvertUtil.convertBigDecimal(laneQcDto.getTotalReads()));
            if (laneQcDto.getQ20() != null)
                qc.setQ20(ConvertUtil.convertBigDecimal(laneQcDto.getQ20()));
            if (laneQcDto.getDimRate() != null)
                qc.setDimRate(ConvertUtil.convertBigDecimal(laneQcDto.getDimRate()));
        }

        qc.setUpdatedBy(UPDATED_BY);
        qc.setUpdatedOn(LocalDateTime.now());
    }

    public static void updateTestResultInfo(TestResultInfo result, TestResultDto dto) {
        if (dto.getTestTime() != null)
            result.setTestTime(ConvertUtil.convertDateTime(dto.getTestTime()));
        if (dto.getDetectionResult() != null)
            result.setTestResult(dto.getDetectionResult());
        if (dto.getReportType() != null)
            result.setReportType(dto.getReportType());
        if (dto.getReportCreateTime() != null)
            result.setReportCreateTime(ConvertUtil.convertDateTime(dto.getReportCreateTime()));
        if (dto.getDownScreening() != null)
            result.setDownScreeningResult(ConvertUtil.convertDownScreening(dto.getDownScreening()));

        if (dto.getZChr13() != null)
            result.setZChr13(ConvertUtil.convertBigDecimal(dto.getZChr13()));
        if (dto.getZChr18() != null)
            result.setZChr18(ConvertUtil.convertBigDecimal(dto.getZChr18()));
        if (dto.getZChr21() != null)
            result.setZChr21(ConvertUtil.convertBigDecimal(dto.getZChr21()));
        if (dto.getTestChr13() != null)
            result.setTestChr13(dto.getTestChr13());
        if (dto.getTestChr18() != null)
            result.setTestChr18(dto.getTestChr18());
        if (dto.getTestChr21() != null)
            result.setTestChr21(dto.getTestChr21());
        if (dto.getTestChrSex() != null)
            result.setTestChrSex(dto.getTestChrSex());
        if (dto.getAbnormalChrX() != null)
            result.setAbnormalChrX(dto.getAbnormalChrX());
        if (dto.getFracAbs() != null)
            result.setFracAbs(ConvertUtil.convertBigDecimal(dto.getFracAbs()));
        if (dto.getFetalFraction() != null)
            result.setFetalFaction(ConvertUtil.convertBigDecimal(dto.getFetalFraction()));
        if (dto.getChildLvChr13() != null)
            result.setChildLvChr13(ConvertUtil.convertBigDecimal(dto.getChildLvChr13()));
        if (dto.getChildLvChr18() != null)
            result.setChildLvChr18(ConvertUtil.convertBigDecimal(dto.getChildLvChr18()));
        if (dto.getChildLvChr21() != null)
            result.setChildLvChr21(ConvertUtil.convertBigDecimal(dto.getChildLvChr21()));
        if (dto.getRiskIndex13() != null)
            result.setRiskIndex13(dto.getRiskIndex13());
        if (dto.getRiskIndex18() != null)
            result.setRiskIndex18(dto.getRiskIndex18());
        if (dto.getRiskIndex21() != null)
            result.setRiskIndex21(dto.getRiskIndex21());
        if (dto.getTestDeletionDuplication() != null)
            result.setTestDeletionDuplication(dto.getTestDeletionDuplication());
        if (dto.getTestChrOther() != null)
            result.setTestChrOther(dto.getTestChrOther());
        if (dto.getAbnormalChrNum() >= 0)
            result.setAbnormalChrNum(dto.getAbnormalChrNum());
        if (dto.getChrN() != null)
            result.setExtraInformation(dto.getChrN());
        if (dto.getRecommendedOperation() != null)
            result.setRecommendedOperation(dto.getRecommendedOperation());
        if (dto.getDoctorOpinion() != null)
            result.setDoctorOpinion(dto.getDoctorOpinion());

        result.setUpdatedBy(UPDATED_BY);
        result.setUpdatedOn(LocalDateTime.now());
    }
}
