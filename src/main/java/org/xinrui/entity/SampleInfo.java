package org.xinrui.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;

/**
 * 样本信息实体类（假设对应sample_info表）,用于去查询数据库获得样本信息
 */
@Data
@TableName("sample_info")
public class SampleInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    // ========== 核心业务字段（按接口文档补充）==========
    private String oldSampleNum;      // 原样本编号（唯一索引）
    private String sampleId;          // 样本编号
    private String productNo;         // 产品套餐编号
    private String productName;       // 产品套餐名称
    private Integer sampleType;       // 样本类型
    private Integer shipmentCondition;// 运输条件
    private Long collectDate;         // 取样日期（时间戳）
    private Long receivedDate;        // 收样日期（时间戳）
    private Integer gestationalWeeks; // 孕周天数
    private Integer fetusType;        // 胎儿类型
    private Integer tubeType;         // 管道类型
    private Integer additionalReportFlag; // 是否需要附加报告

    // ========== 患者信息 ==========
    private String patientName;       // 受检者姓名
    private String patientIdCard;     // 身份证号
    private Long patientBirthday;     // 出生日期（时间戳）
    private Integer patientAge;       // 年龄

    // ========== 医院信息 ==========
    private String clinicNum;         // 门诊号
    private String hospitalName;      // 送检医院
    private String doctorName;        // 送检医生

    // ========== 系统字段 ==========
    private Integer status;           // 样本状态（0-待检测 1-检测中 2-已完成）
    private Date createTime;
    private Date updateTime;

    // TODO: 根据接口文档补充其他字段（如：sampleSource, pregnantHistory等）
}
