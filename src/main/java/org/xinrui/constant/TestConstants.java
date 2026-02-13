package org.xinrui.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 样本信息相关的字典常量类
 * 包含样本类型、运输条件、采血管类型等字段的映射关系
 */
public class TestConstants {

    // 私有构造方法，防止实例化
    private TestConstants() {}



    /**
     * 用于映射样本类型显示名称到代码 (sample_type)
     */
    public static final Map<String, Integer> SAMPLE_TYPE_CODE_MAP = new HashMap<>();
    static {
        SAMPLE_TYPE_CODE_MAP.put("全血", 1);
        SAMPLE_TYPE_CODE_MAP.put("血浆", 2);
        SAMPLE_TYPE_CODE_MAP.put("DNA", 4);
    }



    /**
     * 用于映射运输条件显示名称到代码 (shipment_condition)
     */
    public static final Map<String, Integer> SHIPMENT_CONDITION_CODE_MAP = new HashMap<>();
    static {
        SHIPMENT_CONDITION_CODE_MAP.put("4°C", 1);
        SHIPMENT_CONDITION_CODE_MAP.put("6~35°C", 2);
        SHIPMENT_CONDITION_CODE_MAP.put("dry ice", 4);
    }



    /**
     * 用于映射采血管类型显示名称到代码 (tube_type)
     */
    public static final Map<String, Integer> TUBE_TYPE_CODE_MAP = new HashMap<>();
    static {
        TUBE_TYPE_CODE_MAP.put("Streck管", 1);
        TUBE_TYPE_CODE_MAP.put("EDTA管", 2);
        TUBE_TYPE_CODE_MAP.put("K管", 4);
        TUBE_TYPE_CODE_MAP.put("G管", 8);
        TUBE_TYPE_CODE_MAP.put("X管", 16);
    }



    /**
     * 用于映射附加报告标志显示名称到代码 (additional_report_flag)
     */
    public static final Map<String, Integer> ADDITIONAL_REPORT_FLAG_CODE_MAP = new HashMap<>();
    static {
        ADDITIONAL_REPORT_FLAG_CODE_MAP.put("是", 1);
        ADDITIONAL_REPORT_FLAG_CODE_MAP.put("否", 2);
    }
}