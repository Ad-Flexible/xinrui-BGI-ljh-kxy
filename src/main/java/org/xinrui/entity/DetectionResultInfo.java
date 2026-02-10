package org.xinrui.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;

/**
 * 检测结果实体类（假设对应result_info表）,用于将halos推送过来的数据存储回数据库（当然可能没有这一步，只是假设）
 */
@Data
@TableName("result_info")
public class DetectionResultInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    // ========== 核心业务字段 ==========
    private String sampleId;          // 样本编号（关联sample_info.sample_id）
    private String oldSampleNum;      // 原样本编号（冗余字段，便于查询）
    private String testItem;          // 检测项目（如：NIPT）
    private String resultStatus;      // 结果状态（01-阴性 02-阳性 03-临界值等）
    private String resultDetail;      // 详细结果（JSON格式存储）
    private Long testDate;            // 检测日期（时间戳）
    private Long reportDate;          // 报告日期（时间戳）

    // ========== 风险评估 ==========
    private String riskLevel;         // 风险等级（高/中/低）
    private String chromosome;        // 染色体（如：21三体）
    private Double riskValue;         // 风险值

    // ========== 系统字段 ==========
    private Integer pushStatus;       // 推送状态（0-未推送 1-推送成功 2-推送失败）
    private String pushErrorMsg;      // 推送失败原因
    private Integer retryCount;       // 重试次数
    private Date createTime;
    private Date updateTime;

    // TODO: 根据接口文档补充其他字段（如：zScore, fetalFraction等）
}
