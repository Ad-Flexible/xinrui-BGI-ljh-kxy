package org.xinrui.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * HIS数据库中的样本信息推送dto
 *
 **/
@Data
public class SampleDto {

    @ApiModelProperty(value = "产品套餐编号",required = true)
    @NotBlank(message = "产品套餐编号不能为空")
    private String productNo;

    @ApiModelProperty(value = "产品套餐名称",required = true)
    @NotBlank(message = "产品套餐名称不能为空")
    private String productName;

    @ApiModelProperty(value = "样本编号",required = true)
    @NotBlank(message = "样本编号不能为空")
    private String sampleId;

    @ApiModelProperty(value = "样本类型，其中0为空",required = true)
    @NotNull(message = "样本类型不能为空")
    private int sampleType;

    @ApiModelProperty(value = "运输条件，其中0为空",required = true)
    @NotNull(message = "运输条件不能为空")
    private int shipmentCondition;

    @ApiModelProperty(value = "取样日期，单位：毫秒",required = true)
    @NotNull(message = "取样日期不能为空")
    private long collectDate;

    @ApiModelProperty(value = "收样日期，单位：毫秒",required = true)
    @NotNull(message = "收样日期不能为空")
    private long receivedDate;

    @ApiModelProperty(value = "孕周天数，其中0为空",required = true)
    @NotNull(message = "孕周天数不能为空")
    private int gestationalWeeks;

    @ApiModelProperty(value = "胎儿类型，其中0为空",required = true)
    @NotNull(message = "胎儿类型不能为空")
    private int fetusType;

    @ApiModelProperty(value = "管道类型，其中0为空",required = true)
    @NotNull(message = "管道类型不能为空")
    private int tubeType;

    @ApiModelProperty(value = "是否需要附加报告，其中0为空")
    private int additionalReportFlag;

    @ApiModelProperty(value = "原样本编号")
    private String oldSampleNum;

    @ApiModelProperty(value = "受检者姓名")
    private String patientName;

    @ApiModelProperty(value = "身份证号")
    private String patientIdCard;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "出生日期")
    private LocalDate patientBirthday;

    @ApiModelProperty(value = "年龄[0,100]，其中0为空")
    @Min(value = 0,message = "年龄最小为0")
    @Max(value = 100,message = "年龄最大为100")
    private int patientAge;

    @ApiModelProperty(value = "门诊号")
    private String clinicNum;

    @ApiModelProperty(value = "送检医院，需先系统配置")
    private String hospitalName;

    @ApiModelProperty(value = "送检医生，需先系统配置")
    private String doctorName;

    @ApiModelProperty(value = "身高[0,500]，其中0为空")
    @Min(value = 0,message = "身高最矮为0cm")
    @Max(value = 500,message = "身高最高为500cm")
    private int patientHeight;

    @ApiModelProperty(value = "体重[0,500000]，支持3位小数，单位：克")
    private String patientWeight;

    @ApiModelProperty(value = "联系地址")
    private String patientAddress;

    @ApiModelProperty(value = "家庭电话")
    private String patientTel;

    @ApiModelProperty(value = "手机号码")
    private String patientMobile;

    @ApiModelProperty(value = "紧急联系人")
    private String emergentName;

    @ApiModelProperty(value = "关系")
    private String emergentRelation;

    @ApiModelProperty(value = "联系人电话")
    private String emergentTel;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "预产期")
    private LocalDate patientEdd;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "末次月经")
    private LocalDate lastMenstrualPeriod;

    @ApiModelProperty(value = "绒毛膜，其中0为空")
    private int chorion;

    @ApiModelProperty(value = "唐筛结果，其中0为空")
    private int downScreening;

    @ApiModelProperty(value = "B超检查结果，其中0为空")
    private int bUltrasonography;

    @ApiModelProperty(value = "是否IVF，其中0为空")
    private int ivfFlag;

    @ApiModelProperty(value = "受孕方式，其中0为空")
    private int conceptionMethod;

    @ApiModelProperty(value = "分娩史-孕次，其中0为空")
    private int bhGravidity;

    @ApiModelProperty(value = "分娩史-产次，其中0为空")
    private int bhParity;

    @ApiModelProperty(value = "分娩史-其他")
    private String bhOther;

    @ApiModelProperty(value = "羊膜穿刺")
    private String amniocentesis;

    @ApiModelProperty(value = "既往史")
    private String illnessHistoryPast;

    @ApiModelProperty(value = "现病史")
    private String illnessHistoryPresent;

    @ApiModelProperty(value = "过敏史")
    private String illnessHistoryAllergy;

    @ApiModelProperty(value = "家族遗传病史")
    private String illnessHistoryGenetic;

    @ApiModelProperty(value = "备注")
    private String patientRemark;
}
