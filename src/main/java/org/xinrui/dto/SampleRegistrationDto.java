package org.xinrui.dto;


import lombok.Data;

/**
 * 样本登记DTO
 */
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 样本登记 DTO
 */
@ApiModel("样本登记信息")
@Data
public class SampleRegistrationDto {


    /** 样本编号/实验编号 */
    @ApiModelProperty(value = "样本编号/实验编号")
    @NotBlank(message = "样本编号/实验编号不能为空")
    private String sampleExperimentNo;

    /** 原样本编号 */
    @ApiModelProperty(value = "原样本编号")
    private String originalSampleNo;

    /** 年龄（周岁） */
    @ApiModelProperty(value = "年龄（周岁）")
    private BigDecimal age;

    /** 身高（cm） */
    @ApiModelProperty(value = "身高（cm）")
    private BigDecimal height;

    /** 体重（kg） */
    @ApiModelProperty(value = "体重（kg）")
    private BigDecimal weight;

    /** 证件号 */
    @ApiModelProperty(value = "证件号")
    private String idNumber;

    /** 妊娠史-妊娠次数 */
    @ApiModelProperty(value = "妊娠史-妊娠次数")
    private Integer pregnancyCount;

    /** 生育次数 */
    @ApiModelProperty(value = "生育次数")
    private Integer childbirthCount;

    /** 自然流产次数 */
    @ApiModelProperty(value = "自然流产次数")
    private Integer miscarriageCount;

    /** 是否为试管婴儿（是:true / 否:false） */
    @ApiModelProperty(value = "是否为试管婴儿（是:true / 否:false）")
    private Boolean isTestTubeBaby;

    /** 是否自体供卵（是:true / 否:false） */
    @ApiModelProperty(value = "是否自体供卵（是:true / 否:false）")
    private Boolean isSelfEggDonation;

    /** 是否异体供卵（是:true / 否:false） */
    @ApiModelProperty(value = "是否异体供卵（是:true / 否:false）")
    private Boolean isAllogeneicEggDonation;

    /** 胎儿类型（必填，下拉字典值） */
    @NotBlank(message = "胎儿类型不能为空")
    @ApiModelProperty(value = "胎儿类型（必填，下拉字典值）", required = true)
    private String fetusType;

    /** 孕周（必填） */
    @ApiModelProperty(value = "孕周（必填）", required = true)
    private Integer gestationalWeeks;

    /** 孕天（必填） */
    @NotNull(message = "孕天不能为空")
    @ApiModelProperty(value = "孕天（必填）", required = true)
    private Integer gestationalDays;

    /** 样本类型（必填，下拉字典值） */
    @NotNull(message = "样本类型不能为空")
    @ApiModelProperty(value = "样本类型（必填，下拉字典值）", required = true)
    private String sampleType;

    /** 运输条件（必填，下拉字典值） */
    @NotNull(message = "运输条件不能为空")
    @ApiModelProperty(value = "运输条件（必填，下拉字典值）", required = true)
    private String transportCondition;

    /** 采血管类型（必填，下拉字典值） */
    @NotBlank(message = "采血管类型不能为空")
    @ApiModelProperty(value = "采血管类型（必填，下拉字典值）", required = true)
    private String bloodCollectionTubeType;

    /** 产品套餐（必填，下拉字典值） */
    @NotBlank(message = "产品套餐不能为空")
    @ApiModelProperty(value = "产品套餐（必填，下拉字典值）", required = true)
    private String productPackage;

    /** 手机号 */
    @NotBlank(message = "手机号不能为空")
    @ApiModelProperty(value = "手机号")
    private String phone;

    /** 紧急联系人 */
    @ApiModelProperty(value = "紧急联系人")
    private String emergencyContact;

    /** 联系人电话 */
    @ApiModelProperty(value = "联系人电话")
    private String contactPhone;

    /** 产前检查-超声检查结果（未查/提示异常） */
    @ApiModelProperty(value = "产前检查-超声检查结果（未查/提示异常）")
    private String ultrasoundCheckResult;

    /** 产前检查-减胎日期（若存在减胎操作） */
    @ApiModelProperty(value = "产前检查-减胎日期（若存在减胎操作）")
    private LocalDate reductionDate;

    /** 产前检查-妊娠情况（单胎/双胎或多胎） */
    @ApiModelProperty(value = "产前检查-妊娠情况（单胎/双胎或多胎）")
    private String pregnancySituation;

    /** 产前检查-血清学筛查状态（未做/已做） */
    @ApiModelProperty(value = "产前检查-血清学筛查状态（未做/已做）")
    private String serumScreeningStatus;

    @ApiModelProperty(value = "产前检查-21-三体风险值分母")
    private Integer serumT21Risk;

    @ApiModelProperty(value = "产前检查-18-三体风险值分母")
    private Integer serumT18Risk;


    /** 产前检查-血清学筛查其他情况 */
    @ApiModelProperty(value = "产前检查-血清学筛查其他情况")
    private String serumOther;

    /** 产前检查-预约穿刺诊断状态（无/已预约） */
    @ApiModelProperty(value = "产前检查-预约穿刺诊断状态（无/已预约）")
    private String appointmentPunctureStatus;

    /** 产前检查-预约穿刺诊断日期 */
    @ApiModelProperty(value = "产前检查-预约穿刺诊断日期,若已预约")
    private LocalDate punctureAppointmentDate;

    /** 其它情况-移植手术状态（无/有） */
    @ApiModelProperty(value = "其它情况-移植手术状态（无/有）")
    private String transplantSurgeryStatus;

    /** 其它情况-移植手术日期 */
    @ApiModelProperty(value = "其它情况-移植手术日期")
    private LocalDate transplantDate;

    /** 其它情况-异体输血状态（无/有） */
    @ApiModelProperty(value = "其它情况-异体输血状态（无/有）")
    private String allogeneicTransfusionStatus;

    /** 其它情况-异体输血日期 */
    @ApiModelProperty(value = "其它情况-异体输血日期")
    private LocalDate transfusionDate;

    /** 其它情况-免疫治疗状态（无/有） */
    @ApiModelProperty(value = "其它情况-免疫治疗状态（无/有）")
    private String immunotherapyStatus;

    /** 其它情况-免疫治疗日期 */
    @ApiModelProperty(value = "其它情况-免疫治疗日期")
    private LocalDate immunotherapyDate;

    /** 其它情况-免疫治疗类型 */
    @ApiModelProperty(value = "其它情况-免疫治疗类型")
    private String immunotherapyType;

    /** 特殊情况 */
    @ApiModelProperty(value = "特殊情况")
    private String specialSituation;

    /** 备注 */
    @ApiModelProperty(value = "备注")
    private String remark;
}
