package org.xinrui.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.xinrui.entity.PatientInfo;
import org.xinrui.mapper.PatientInfoMapper;
import org.xinrui.service.MchiPatientService;

@Service
public class MchiPatientServiceImpl extends ServiceImpl<PatientInfoMapper, PatientInfo> implements MchiPatientService {
}
