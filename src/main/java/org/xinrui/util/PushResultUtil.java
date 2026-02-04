package org.xinrui.util;

import org.springframework.stereotype.Component;
import org.xinrui.dto.PushResultRequestDTO;
import org.xinrui.dto.RequestSampleResponseDTO;
import org.xinrui.entity.ResultInfo;
import org.xinrui.entity.SampleInfo;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;


@Component
public class PushResultUtil {

    private final Validator validator;

    // 构造器注入 Validator
    public PushResultUtil(Validator validator) {
        this.validator = validator;
    }

    /**
     * DTO 转 entity
     * 复用 BeanConvertUtil 简化代码
     */
    public ResultInfo convertToEntity(PushResultRequestDTO pushResultRequestDTO) {
        if (pushResultRequestDTO == null) {
            return null;
        }
        // 使用之前定义的 BeanConvertUtil,当然也可自定义
        return BeanConvertUtil.convert(pushResultRequestDTO, ResultInfo.class);
    }


}
