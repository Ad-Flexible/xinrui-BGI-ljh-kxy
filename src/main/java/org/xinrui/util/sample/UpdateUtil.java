package org.xinrui.util.sample;

import org.xinrui.dto.SampleRegistrationDto;
import org.xinrui.entity.ExaminationInfo;
import org.xinrui.entity.PatientInfo;
import org.xinrui.entity.SampleInfo;
import org.xinrui.util.ConvertUtil;

import java.time.LocalDateTime;

public class UpdateUtil {
    private static final Long UPDATED_BY = 1L; // 固定更新人ID
    private static final int INVERT = 1; // 固定更新版本值为1

    public static void updateSampleInfo(SampleInfo sampleInfo,SampleRegistrationDto dto, Long patientOid){
        //to do
        sampleInfo.setOldSampleNum(dto.getOldSampleNum());
        sampleInfo.setSampleType(ConvertUtil.convertSampleType(dto.getSampleType()));
        sampleInfo.setShipmentCondition(ConvertUtil.convertShipmentCondition(dto.getShipmentCondition()));
        sampleInfo.setTubeType(ConvertUtil.convertTubeType(dto.getTubeType()));
        sampleInfo.setAdditionalReportFlag(1); // 默认为"是"
        sampleInfo.setRepeatCount(1); // 默认为1
        sampleInfo.setProductNo(dto.getProductName());
        sampleInfo.setProductName(dto.getProductName());
        sampleInfo.setUpdatedBy(UPDATED_BY);
        sampleInfo.setUpdatedOn(LocalDateTime.now());
    }

    public static void updatePatientInfo(PatientInfo patientInfo,SampleRegistrationDto dto){
        //to do
        patientInfo.setPhone(dto.getPhone());
        patientInfo.setIdentity(dto.getIdentity());
        patientInfo.setEmergencyContact(dto.getEmergencyContact());
        patientInfo.setEmergencyContactPhone(dto.getEmergencyContactPhone());
        patientInfo.setUpdatedBy(UPDATED_BY);
        patientInfo.setUpdatedOn(LocalDateTime.now());
    }

    public static void updateExaminationInfo(ExaminationInfo exam,SampleRegistrationDto dto, Long sampleOid){
        //to do
        exam.setPatientAge(dto.getAge());
        exam.setPatientHeight(dto.getHeight());
        exam.setPatientWeight(dto.getWeight());
        exam.setFetusType(ConvertUtil.convertFetusType(dto.getFetusType()));
        exam.setGestationalWeeks(dto.getGestationalWeeks());
        exam.setGestationalDays(dto.getGestationalDays());
        exam.setBUltrasonography(ConvertUtil.convertBUltrasonography(dto.getUsCheck()));
        exam.setIvfFlag(dto.getIvfFlag());
        exam.setTubebabyType(dto.getTubebabyType());
        exam.setBhGravidity(dto.getBhGravidity());
        exam.setBhParity(dto.getBhParity());
        exam.setBhOther(dto.getBhOther());
        exam.setPatientRemark(dto.getRemark());
        exam.setDownSymdromeFlag(dto.getDownSymdromeFlag());
        exam.setDownSymdromeResult1(dto.getDownSymdromeResult1());
        exam.setDownSymdromeResult2(dto.getDownSymdromeResult2());
        exam.setDownSymdromeResultOth(dto.getDownSymdromeResultOth());
        exam.setPunctureAppointment(dto.getPunctureAppointment());
        exam.setPunctureAppointmentDate(ConvertUtil.convertDateTime(dto.getPunctureAppointmentDate()));
        exam.setTransplantation(dto.getTransplantation());
        exam.setTransplantationDate(ConvertUtil.convertDateTime(dto.getTransplantationDate()));
        exam.setAllogeneicTransfusion(dto.getAllogeneicTransfusion());
        exam.setAllogeneicTransfusionDate(ConvertUtil.convertDateTime(dto.getAllogeneicTransfusionDate()));
        exam.setImmunotherapy(dto.getImmunotherapy());
        exam.setImmunotherapyDate(ConvertUtil.convertDateTime(dto.getImmunotherapyDate()));
        exam.setImmunotherapyType(dto.getImmunotherapyType());
        exam.setSpecialCase(dto.getSpecialCase());
        exam.setUpdatedBy(UPDATED_BY);
        exam.setUpdatedOn(LocalDateTime.now());
    }
}
