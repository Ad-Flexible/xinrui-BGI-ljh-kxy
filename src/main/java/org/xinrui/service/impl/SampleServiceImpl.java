package org.xinrui.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xinrui.dto.SampleDto;
import org.xinrui.dto.SampleRegistrationDto;
import org.xinrui.entity.PatientInfo;
import org.xinrui.entity.SampleInfo;
import org.xinrui.exception.BusinessException;
import org.xinrui.mapper.SampleMapper;
import org.xinrui.service.SampleService;
import org.xinrui.util.SampleUtil;

@Slf4j
@Service
public class SampleServiceImpl implements SampleService {

    @Autowired
    private SampleMapper sampleMapper;



    @Override
    @Transactional(readOnly = true)
    public SampleDto getSample(String oldSampleNum) {
        log.info("查询样本信息，原样本编号: {}", oldSampleNum);

        // 1. 查询数据库（优先使用唯一索引字段）
        SampleDto sampleDto = sampleMapper.selectByOldSampleNum(oldSampleNum);
        if (sampleDto == null) {
            log.warn("样本信息不存在，原样本编号: {}", oldSampleNum);
            throw new BusinessException("100110103", "请求的资源不存在");
        }

        //2.属性的校验，确保部分字段的必填以及数值的规定
        SampleUtil.RequestSampleValidate(sampleDto);

        log.info("样本信息查询成功，样本编号: {}", sampleDto.getSampleId());
        return sampleDto;
    }

    @Override
    @Transactional
    public boolean handleSampleRegistrationInfo(SampleRegistrationDto sampleRegistrationDto) {

        log.info("样本登记信息处理，样本编号为: {}", sampleRegistrationDto.getSampleId());

        //to do :将sampleRegistrationDto拆分为examinationInfo、sampleInfo和patientInfo
        //to do :对patientInfo、sampleInfo、examinationInfo进行校验，看看是否是新增还是更新

        PatientInfo patientInfo = handlePatientInfo(sampleRegistrationDto);

        SampleInfo sampleInfo = handleSampleInfo(sampleRegistrationDto,patientInfo.getOid());

        handleExaminationInfo(sampleRegistrationDto,sampleInfo.getOid());


        return true;
    }

    @Override
    @Transactional
    public PatientInfo handlePatientInfo(SampleRegistrationDto sampleRegistrationDto){
        //to do 待完善
        //具体校验方法：patientInfo利用证件号或者手机号来查询数据库，若无返回数据则三表数据均为新增，
        // 有数据返回则对patientInfo作更新，继续校验sampleInfo的存在
        return null;
    }

    @Override
    @Transactional
    public SampleInfo handleSampleInfo(SampleRegistrationDto sampleRegistrationDto,Long patientOid){
        //to do 待完善
        //具体校验方法：sampleInfo利用样本编号来查询数据库，若无返回数据则sampleInfo和examinationInfo为新增,
        // 有数据返回则对sampleInfo作更新，获取sample_oid到继续校验examinationInfo的存在
        return null;
    }

    @Override
    @Transactional
    public void handleExaminationInfo(SampleRegistrationDto sampleRegistrationDto, Long sampleOid){
        //to do 待完善
        //具体校验方法：examinationInfo利用关联的sample_oid来查询数据库，若无返回数据则examinationInfo为新增,有则更新
    }

}
