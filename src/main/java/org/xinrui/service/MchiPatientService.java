package org.xinrui.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import org.xinrui.entity.PatientInfo;


public interface MchiPatientService extends IService<PatientInfo> {
    boolean removeWithCascade(Long oid); // 新增级联删除方法
}
