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
 * 采集建档表实体类
 */
@ApiModel(value = "ScreeningArchives", description = "采集建档表")
@Data
@TableName("t_mchi_screening_archives")
public class ScreeningArchiveInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "档案表的主键", example = "1001")
    private Long oid;

    @ApiModelProperty(value = "邮政编码", example = "100101")
    @TableField("postal_code")
    private String postalCode;

    @ApiModelProperty(value = "联系电话", example = "13800138000")
    @TableField("phone")
    private String phone;

    @ApiModelProperty(value = "性别", example = "1")
    @TableField("sex")
    private Integer sex;

    @ApiModelProperty(value = "出生时间", example = "2026-02-17")
    @TableField("birth_date")
    private LocalDate birthDate;

    @ApiModelProperty(value = "登记医生", example = "2001")
    @TableField("register_doctor_oid")
    private Long registerDoctorOid;

    @ApiModelProperty(value = "登记日期", example = "2026-02-17")
    @TableField("register_date")
    private LocalDate registerDate;

    @ApiModelProperty(value = "登记机构", example = "tenant_001")
    @TableField("register_mechanism")
    private String registerMechanism;

    @ApiModelProperty(value = "住院号", example = "ZY20260217001")
    @TableField("admission_number")
    private String admissionNumber;

    @ApiModelProperty(value = "详细地址", example = "北京市朝阳区某某街道")
    @TableField("address")
    private String address;

    @ApiModelProperty(value = "母亲名字", example = "李梅")
    @TableField("mom_name")
    private String momName;

    @ApiModelProperty(value = "所属市", example = "北京市")
    @TableField("city")
    private String city;

    @ApiModelProperty(value = "所属县", example = "朝阳区")
    @TableField("county")
    private String county;

    @ApiModelProperty(value = "采血机构", example = "1001")
    @TableField("dept_id")
    private Long deptId;

    @ApiModelProperty(value = "孕周", example = "40")
    @TableField("gestational_age")
    private Long gestationalAge;

    @ApiModelProperty(value = "分娩方式", example = "1")
    @TableField("childbirth_mode")
    private Long childbirthMode;

    @ApiModelProperty(value = "体重(克)", example = "3500")
    @TableField("weight")
    private Integer weight;

    @ApiModelProperty(value = "母亲手机", example = "13800138001")
    @TableField("mom_phone")
    private String momPhone;

    @ApiModelProperty(value = "父亲手机", example = "13800138002")
    @TableField("dad_phone")
    private String dadPhone;

    @ApiModelProperty(value = "户口", example = "1")
    @TableField("registered_residence")
    private Long registeredResidence;

    @ApiModelProperty(value = "地区", example = "1")
    @TableField("region")
    private Long region;

    @ApiModelProperty(value = "耳聋,0=无,1=父亲耳聋,2=母亲耳聋,3=父母耳聋", example = "0")
    @TableField("deaf")
    private Integer deaf;

    @ApiModelProperty(value = "听力筛查", example = "PASS")
    @TableField("hearing_screening")
    private String hearingScreening;

    @ApiModelProperty(value = "添加科室", example = "01")
    @TableField("department")
    private String department;

    @ApiModelProperty(value = "创建时间", example = "2026-02-17 10:00:00")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间", example = "2026-02-17 10:00:00")
    @TableField("update_time")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "his中患者id", example = "PAT20260217001")
    @TableField("patient_id_his")
    private String patientIdHis;

    @ApiModelProperty(value = "his中患者就诊卡号", example = "CARD20260217001")
    @TableField("patient_card_id")
    private String patientCardId;

    @ApiModelProperty(value = "门诊处方id", example = "RX20260217001")
    @TableField("outpatient_recipe_id")
    private String outpatientRecipeId;

    @ApiModelProperty(value = "筛查阶段 1-怀孕前 2-怀孕中 3-宝宝已出生", example = "3")
    @TableField("check_period")
    private Integer checkPeriod;

    @ApiModelProperty(value = "支付方式 0 现金 1 银行卡 2 支票 3 IC卡(医保卡) 4-银行汇款单 5-支付宝 6-微信 7-移动支付", example = "0")
    @TableField("pay_type_int")
    private Integer payTypeInt;

    @ApiModelProperty(value = "是否为门诊业务 0-否 1-是", example = "0")
    @TableField("opt_biz_flag")
    private Short optBizFlag;
}