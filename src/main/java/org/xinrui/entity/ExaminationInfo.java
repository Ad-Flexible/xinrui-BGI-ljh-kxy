package org.xinrui.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 患者检查表
 */
@ApiModel(value = "ExaminationInfo", description = "患者检查表")
@Data
@TableName("t_lis_examination")
public class ExaminationInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键", example = "1001")
    @TableField("oid")
    private Long oid;

    @ApiModelProperty(value = "样本主键", example = "2001")
    @TableField("sample_oid")
    private Long sampleOid;

    @ApiModelProperty(value = "年龄", example = "30")
    @TableField("patient_age")
    private Integer patientAge;

    @ApiModelProperty(value = "身高[0,500]，支持2位小数", example = "175.500")
    @TableField("patient_height")
    private BigDecimal patientHeight;

    @ApiModelProperty(value = "体重[0,500]，支持2位小数", example = "65.250")
    @TableField("patient_weight")
    private BigDecimal patientWeight;

    @ApiModelProperty(value = "预产期", example = "2023-12-15")
    @TableField("patient_edd")
    private LocalDate patientEdd;

    @ApiModelProperty(value = "胎儿类型代码:1. 单胎、2. 双胎", example = "1")
    @TableField("fetus_type")
    private Integer fetusType;

    @ApiModelProperty(value = "孕周（整数）", example = "32")
    @TableField("gestational_weeks")
    private Integer gestationalWeeks;

    @ApiModelProperty(value = "孕天（0-6）", example = "3")
    @TableField("gestational_days")
    private Integer gestationalDays;

    @ApiModelProperty(value = "末次月经", example = "2023-01-10")
    @TableField("last_menstrual_period")
    private LocalDate lastMenstrualPeriod;

    @ApiModelProperty(value = "绒毛膜类型:1. DC、2. DA、4. MC、8. DCDA、16. MCMA、32. MCDA", example = "2")
    @TableField("chorion_type")
    private Integer chorionType;

    @ApiModelProperty(value = "B超检查结果", example = "1")
    @TableField("b_ultrasonography")
    private Integer bUltrasonography;

    @ApiModelProperty(value = "是否IVF Y/N,枚举：1.是、2.否", example = "1")
    @TableField("ivf_flag")
    private Integer ivfFlag;

    @ApiModelProperty(value = "受孕方式：1. 自然受孕、2. IUI、4. IVF-ET", example = "3")
    @TableField("conception_method")
    private Integer conceptionMethod;

    @ApiModelProperty(value = "孕次", example = "2")
    @TableField("bh_gravidity")
    private Integer bhGravidity;

    @ApiModelProperty(value = "产次", example = "1")
    @TableField("bh_parity")
    private Integer bhParity;

    @ApiModelProperty(value = "其他分娩史", example = "剖宫产")
    @TableField("bh_other")
    private String bhOther;

    @ApiModelProperty(value = "羊水穿刺结果", example = "正常")
    @TableField("amniocentesis_result")
    private String amniocentesisResult;

    @ApiModelProperty(value = "既往史", example = "无高血压病史")
    @TableField("illness_history_past")
    private String illnessHistoryPast;

    @ApiModelProperty(value = "现病史/临床诊断", example = "妊娠期糖尿病")
    @TableField("illness_history_present")
    private String illnessHistoryPresent;

    @ApiModelProperty(value = "过敏史", example = "青霉素过敏")
    @TableField("illness_history_allergy")
    private String illnessHistoryAllergy;

    @ApiModelProperty(value = "家族遗传病史", example = "无")
    @TableField("illness_history_genetic")
    private String illnessHistoryGenetic;

    @ApiModelProperty(value = "备注", example = "定期产检")
    @TableField("patient_remark")
    private String patientRemark;

    @ApiModelProperty(value = "数据版本(审计字段)", example = "0")
    @TableField("intver")
    private Integer intver;

    @ApiModelProperty(value = "最后更新人ID(审计字段)", example = "5001")
    @TableField("updated_by")
    private Long updatedBy;

    @ApiModelProperty(value = "更新时间(审计字段)", example = "2023-08-15T16:00:00")
    @TableField("updated_on")
    private LocalDateTime updatedOn;
}
