package org.xinrui.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.xinrui.Application;
import org.xinrui.dto.SampleDto;
import org.xinrui.mapper.SampleMapper;

/**
 * 仅用于测试SampleMapper和打印SampleDto所有字段的测试类
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
public class SampleMapperPrintTest {

    @Autowired
    private SampleMapper sampleMapper;

    /**
     * 测试查询并打印所有字段
     * 使用已知存在的样本编号进行测试
     */
    @Test
    public void testPrintAllFields() {
        // 替换为数据库中真实存在的样本编号
        String oldSampleNum = "HOSP240312001";

        // 执行查询
        SampleDto dto = sampleMapper.selectByOldSampleNum(oldSampleNum);

        if (dto == null) {
            System.out.println("未找到匹配的样本信息，原样本编号: " + oldSampleNum);
            return;
        }

        System.out.println("\n===== 查询结果 (SampleDto) =====");
        System.out.println("sampleId: " + dto.getSampleId());
        System.out.println("oldSampleNum: " + dto.getOldSampleNum());
        System.out.println("sampleType: " + dto.getSampleType());
        System.out.println("collectDate: " + dto.getCollectDate());
        System.out.println("receivedDate: " + dto.getReceivedDate());
        System.out.println("shipmentCondition: " + dto.getShipmentCondition());
        System.out.println("gestationalWeeks: " + dto.getGestationalWeeks());
        System.out.println("fetusType: " + dto.getFetusType());
        System.out.println("tubeType: " + dto.getTubeType());
        System.out.println("additionalReportFlag: " + dto.getAdditionalReportFlag());
        System.out.println("patientName: " + dto.getPatientName());
        System.out.println("patientIdCard: " + dto.getPatientIdCard());
        System.out.println("patientBirthday: " + dto.getPatientBirthday());
        System.out.println("patientAge: " + dto.getPatientAge());
        System.out.println("clinicNum: " + dto.getClinicNum());
        System.out.println("hospitalName: " + dto.getHospitalName());
        System.out.println("doctorName: " + dto.getDoctorName());
        System.out.println("patientHeight: " + dto.getPatientHeight());
        System.out.println("patientWeight: " + dto.getPatientWeight());
        System.out.println("patientAddress: " + dto.getPatientAddress());
        System.out.println("patientTel: " + dto.getPatientTel());
        System.out.println("patientMobile: " + dto.getPatientMobile());
        System.out.println("emergentName: " + dto.getEmergentName());
        System.out.println("emergentRelation: " + dto.getEmergentRelation());
        System.out.println("emergentTel: " + dto.getEmergentTel());
        System.out.println("patientEdd: " + dto.getPatientEdd());
        System.out.println("lastMenstrualPeriod: " + dto.getLastMenstrualPeriod());
        System.out.println("chorion: " + dto.getChorion());
        System.out.println("downScreening: " + dto.getDownScreening());
        System.out.println("bUltrasonography: " + dto.getBUltrasonography());
        System.out.println("ivfFlag: " + dto.getIvfFlag());
        System.out.println("conceptionMethod: " + dto.getConceptionMethod());
        System.out.println("bhGravidity: " + dto.getBhGravidity());
        System.out.println("bhParity: " + dto.getBhParity());
        System.out.println("bhOther: " + dto.getBhOther());
        System.out.println("amniocentesis: " + dto.getAmniocentesis());
        System.out.println("illnessHistoryPast: " + dto.getIllnessHistoryPast());
        System.out.println("illnessHistoryPresent: " + dto.getIllnessHistoryPresent());
        System.out.println("illnessHistoryAllergy: " + dto.getIllnessHistoryAllergy());
        System.out.println("illnessHistoryGenetic: " + dto.getIllnessHistoryGenetic());
        System.out.println("patientRemark: " + dto.getPatientRemark());
        System.out.println("================================\n");
    }
}
