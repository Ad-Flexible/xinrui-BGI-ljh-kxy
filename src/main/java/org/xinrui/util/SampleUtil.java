package org.xinrui.util;

/*
 * 包含entity->dto方法
 * 以及对于RequestSampleResponseDTO的一些校验（非空以及数值校验这些）
 * */

import org.xinrui.dto.SampleDto;
import org.xinrui.entity.SampleInfo;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.stream.Collectors;

public class SampleUtil {

    // 使用静态Validator实例
    private static final Validator VALIDATOR;

    static {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            VALIDATOR = factory.getValidator();
        }
    }

    /**
     * Entity 转 DTO
     */
    public static SampleDto convertToDTO(SampleInfo sampleInfo) {
        if (sampleInfo == null) {
            return null;
        }
        return BeanConvertUtil.convert(sampleInfo, SampleDto.class);
    }

    /**
     * 校验 DTO 对象
     *
     * @param dto 待校验对象
     * @throws javax.validation.ValidationException 如果校验失败，抛出异常包含详细错误信息
     */
    public static void RequestSampleValidate(SampleDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("待校验对象不能为 null");
        }

        // 执行校验
        Set<ConstraintViolation<SampleDto>> violations = VALIDATOR.validate(dto);

        if (!violations.isEmpty()) {
            // 收集所有错误信息拼接成字符串
            String errorMessage = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(", "));

            throw new javax.validation.ValidationException("数据校验失败: " + errorMessage);
        }
    }
}
