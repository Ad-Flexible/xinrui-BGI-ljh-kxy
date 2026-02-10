package org.xinrui.util;

/*
* 包含entity->dto方法
* 以及对于RequestSampleResponseDTO的一些校验（非空以及数值校验这些）
* */

import org.springframework.stereotype.Component;
import org.xinrui.dto.SampleDto;
import org.xinrui.entity.SampleInfo;

import javax.validation.Validator;
import javax.validation.ConstraintViolation;
import java.util.Set;
import java.util.stream.Collectors;

@Component // 建议交给Spring管理，以便注入Validator；如果必须是纯静态工具类，请看下文说明
public class RequestSampleUtil {

    private final Validator validator;

    // 构造器注入 Validator
    public RequestSampleUtil(Validator validator) {
        this.validator = validator;
    }

    /**
     * Entity 转 DTO
     * 复用 BeanConvertUtil 简化代码
     */
    public SampleDto convertToDTO(SampleInfo sampleInfo) {
        if (sampleInfo == null) {
            return null;
        }
        // 使用之前定义的 BeanConvertUtil,当然也可自定义
        return BeanConvertUtil.convert(sampleInfo, SampleDto.class);
    }

    /**
     * 校验 DTO 对象
     *
     * @param dto 待校验对象
     * @throws javax.validation.ValidationException 如果校验失败，抛出异常包含详细错误信息
     */
    public void RequestSampleValidate(SampleDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("待校验对象不能为 null");
        }

        // 执行校验
        Set<ConstraintViolation<SampleDto>> violations = validator.validate(dto);

        if (!violations.isEmpty()) {
            // 收集所有错误信息拼接成字符串
            String errorMessage = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(", "));

            throw new javax.validation.ValidationException("数据校验失败: " + errorMessage);
        }
    }
}
