package org.xinrui.util.testResult;

import lombok.extern.slf4j.Slf4j;
import org.xinrui.constant.TestConstants;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Slf4j
public class ConvertUtil {

//    private static Integer convertToCode(Map<String, Integer> dictMap, String displayName) {
//        if (displayName == null || displayName.isEmpty()) {
//            return null;
//        }
//        return dictMap.get(displayName);
//    }
//
//    private static String convertToCode(Map<String, String> dictMap, String displayName) {
//        if (displayName == null || displayName.isEmpty()) {
//            return null;
//        }
//        return dictMap.get(displayName);
//    }

    private static <T> T convertToCode(Map<String, T> dictMap, String displayName) {
        if (displayName == null || displayName.isEmpty()) {
            return null;
        }
        return dictMap.get(displayName);
    }

    // ==================== 样本信息转换 ====================
    public static Integer convertSampleType(String type) {
        return convertToCode(TestConstants.SAMPLE_TYPE_CODE_MAP, type);
    }

    public static Integer convertShipmentCondition(String condition) {
        return convertToCode(TestConstants.SHIPMENT_CONDITION_CODE_MAP, condition);
    }

    public static Integer convertTubeType(String tube) {
        return convertToCode(TestConstants.TUBE_TYPE_CODE_MAP, tube);
    }

    public static Integer convertGestationalWeeks(String weeks) {
        if (weeks == null){
            return null;
        }
        String[] parts = weeks.split(",");
        return Integer.parseInt(parts[0].trim());
    }

    public static Integer convertFetusType(String type) {
        return convertToCode(TestConstants.FETUS_TYPE_CODE_MAP, type);
    }

    // ==================== 检查信息转换 ====================
    public static Integer convertChorion(String chorion) {
        return convertToCode(TestConstants.CHORION_TYPE_CODE_MAP, chorion);
    }

    public static Integer convertBUltrasonography(String result) {
        return convertToCode(TestConstants.B_ULTRASONOGRAPHY_CODE_MAP, result);
    }

    public static Integer convertIvfFlag(String flag) {
        return convertToCode(TestConstants.IVF_FLAG_CODE_MAP, flag);
    }

    public static Integer convertConceptionMethod(String method) {
        return convertToCode(TestConstants.CONCEPTION_METHOD_CODE_MAP, method);
    }

    // ==================== 检测结果转换 ====================
    public static Integer convertDownScreening(String result) {
        return convertToCode(TestConstants.DOWN_SCREENING_RESULT_CODE_MAP, result);
    }

    // ==================== 日期转换 ====================
    public static LocalDateTime convertDateTime(String dateTime) {
        if (dateTime == null) {
            return null;
        }
        return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public static LocalDate convertDate(String date) {
        if (date == null) {
            return null;
        }
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    // ==================== 数值转换 ====================
    public static BigDecimal convertBigDecimal(Double value) {
        return value == null ? null : BigDecimal.valueOf(value);
    }

    /**
     * 将带单位的字符串转换为BigDecimal
     * 例如："28.39M" -> 28.39
     * @param value 带单位的字符串值
     * @return BigDecimal值，如果输入为null或无法解析则返回null
     */
    public static BigDecimal convertBigDecimal(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        try {
            // 移除所有非数字字符（除了小数点和负号）
            String numericValue = value.replaceAll("[^0-9.-]", "");
            if (numericValue.isEmpty()) {
                return null;
            }
            return new BigDecimal(numericValue);
        } catch (NumberFormatException e) {
            // 如果转换失败，记录日志并返回null
            log.error("string to BigDecimal转换失败: {}", value, e);
            return null;
        }
    }

    // ==================== CNV类别转换 ====================
    public static String convertCnvCategory(String category) {
        return convertToCode(TestConstants.CNV_CATEGORY_CODE_MAP, category);
    }
}