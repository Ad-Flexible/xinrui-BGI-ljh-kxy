package org.xinrui.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PatientDTO {

    @ApiModelProperty(value = "产品套餐编号")
    private String productNo;

    @ApiModelProperty(value = "产品套餐名称")
    private String productName;

    @ApiModelProperty(value = "样本编号")
    private String sampleId;

    @ApiModelProperty(value = "样本类型，其中0为空")
    private int sampleType;

    @ApiModelProperty(value = "运输条件，其中0为空")
    private int shipmentCondition;

    @ApiModelProperty(value = "取样日期，单位：毫秒")
    private long collectDate;

    @ApiModelProperty(value = "收样日期，单位：毫秒")
    private long receivedDate;

    @ApiModelProperty(value = "孕周天数，其中0为空")
    private int gestationalWeeks;

    @ApiModelProperty(value = "胎儿类型，其中0为空")
    private int fetusType;

    @ApiModelProperty(value = "管道类型，其中0为空")
    private int tubeType;

    @ApiModelProperty(value = "是否需要附加报告，其中0为空")
    private int additionalReportFlag;

    @ApiModelProperty(value = "原样本编号")
    private String oldSampleNum;

    @ApiModelProperty(value = "受检者姓名")
    private String patientName;

    @ApiModelProperty(value = "患者身份证号")
    private String patientIdCard;

    @ApiModelProperty(value = "出生日期，单位：毫秒")
    private long patientBirthday;

    @ApiModelProperty(value = "年龄[0,100]，其中0为空")
    private int patientAge;

    @ApiModelProperty(value = "门诊号")
    private String clinicNum;

    @ApiModelProperty(value = "送检医院，需先系统配置")
    private String hospitalName;

    @ApiModelProperty(value = "送检医生，需先系统配置")
    private String doctorName;

    @ApiModelProperty(value = "身高[0,500]，其中0为空")
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

    @ApiModelProperty(value = "预产期，单位：毫秒")
    private String patientEdd;

    @ApiModelProperty(value = "末次月经，单位：毫秒")
    private String lastMenstrualPeriod;

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
