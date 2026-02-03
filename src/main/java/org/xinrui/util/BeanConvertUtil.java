package org.xinrui.util;

import org.springframework.beans.BeanUtils;

/**
 * Bean转换工具类（简化版，生产环境建议用MapStruct）
 */
public class BeanConvertUtil {

    public static <T> T convert(Object source, Class<T> targetClass) {
        try {
            T target = targetClass.newInstance();
            BeanUtils.copyProperties(source, target);
            return target;
        } catch (Exception e) {
            throw new RuntimeException("Bean转换失败", e);
        }
    }
}