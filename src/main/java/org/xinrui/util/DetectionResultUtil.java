package org.xinrui.util;

import org.springframework.stereotype.Component;
import org.xinrui.dto.detectionresult.DetectionResultDto;
import org.xinrui.entity.*;

import javax.validation.Validator;


@Component
public class DetectionResultUtil {

    private final Validator validator;

    // 构造器注入 Validator
    public DetectionResultUtil(Validator validator) {
        this.validator = validator;
    }

    /**
     * DTO 转 entity
     * 复用 BeanConvertUtil 简化代码
     */
    public TestResultInfo convertToEntity(DetectionResultDto pushResultRequestDTO) {
        if (pushResultRequestDTO == null) {
            return null;
        }
        // 使用之前定义的 BeanConvertUtil,当然也可自定义
        return BeanConvertUtil.convert(pushResultRequestDTO, TestResultInfo.class);
    }


}
