package org.xinrui.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 患者信息表
 */
@ApiModel(value = "PatientInfo", description = "患者临床信息表")
@Data
@TableName("t_mchi_patient")
public class PatientInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键", example = "1001")
    @TableField("oid")
    private Long oid;

    @ApiModelProperty(value = "孕妇姓名", example = "张三")
    @TableField("patient_name")
    private String patientName;

    @ApiModelProperty(value = "身份证号", example = "110101199001011234")
    @TableField("patient_id_card")
    private String patientIdCard;

    @ApiModelProperty(value = "手机号", example = "13800138000")
    @TableField("patient_mobile")
    private String patientMobile;

    @ApiModelProperty(value = "联系地址", example = "北京市海淀区中关村大街1号")
    @TableField("patient_address")
    private String patientAddress;

    @ApiModelProperty(value = "出生日期", example = "1990-01-01")
    @TableField("patient_birthday")
    private LocalDate patientBirthday;

    @ApiModelProperty(value = "家庭电话", example = "010-88889999")
    @TableField("patient_tel")
    private String patientTel;

    @ApiModelProperty(value = "紧急联系人", example = "李四")
    @TableField("emergent_name")
    private String emergentName;

    @ApiModelProperty(value = "与紧急联系人的关系", example = "丈夫")
    @TableField("emergent_relation")
    private String emergentRelation;

    @ApiModelProperty(value = "紧急联系人电话", example = "13900139000")
    @TableField("emergent_tel")
    private String emergentTel;

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