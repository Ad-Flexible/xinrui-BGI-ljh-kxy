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

@ApiModel(value = "BloodFilmManagement", description = "血膜管理表")
@Data
@TableName("t_mchi_blood_film_management")
public class BloodFilmManagementInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "血片管理表的 oid", example = "1001")
    private Long oid;

    @ApiModelProperty(value = "对应筛查档案表 t_mchi_screening_archives 的 oid", example = "2001")
    @TableField("screening_archives_id")
    private Long screeningArchivesId;

    @ApiModelProperty(value = "采血卡号", example = "CARD20260310001")
    @TableField("specimen_number")
    private String specimenNumber;

    @ApiModelProperty(value = "采血时间", example = "2026-03-10")
    @TableField("blood_collection_time")
    private LocalDate bloodCollectionTime;

    @ApiModelProperty(value = "采血方式 1 针刺采血 2 静脉采血 3 动脉采血 4 其他", example = "1")
    @TableField("collection_blood_method")
    private String collectionBloodMethod;

    @ApiModelProperty(value = "采血部位 1 足跟 2 手指 3 股静脉 4 颈静脉 5 脐静脉 6 桡静脉 7 其他", example = "1")
    @TableField("collection_blood_position")
    private String collectionBloodPosition;

    @ApiModelProperty(value = "采血员名字", example = "张护士")
    @TableField("collector_blood_name")
    private String collectorBloodName;

    @ApiModelProperty(value = "卡片状态 1 未接收 2 已接收 3 作废 4 已筛查", example = "1")
    @TableField("card_status")
    private String cardStatus;

    @ApiModelProperty(value = "审核人", example = "3001")
    @TableField("reviewer")
    private Long reviewer;

    @ApiModelProperty(value = "审核日期", example = "2026-03-10")
    @TableField("audit_date")
    private LocalDate auditDate;

    @ApiModelProperty(value = "审核机构", example = "北京市妇幼保健院")
    @TableField("audit_organization")
    private String auditOrganization;

    @ApiModelProperty(value = "卡片接收日期", example = "2026-03-10")
    @TableField("card_receiving_date")
    private LocalDate cardReceivingDate;

    @ApiModelProperty(value = "卡片接收人", example = "4001")
    @TableField("card_recipient")
    private Long cardRecipient;

    @ApiModelProperty(value = "卡片接收机构", example = "北京市妇幼保健院检验科")
    @TableField("card_receiving_mechanism")
    private String cardReceivingMechanism;

    @ApiModelProperty(value = "作废原因 1 固化重采 2 血片污染 3 未渗透 4 血斑过小 5 渗血环", example = "1")
    @TableField("reasons_invalidation")
    private String reasonsInvalidation;

    @ApiModelProperty(value = "实验编号", example = "SMP20260310001")
    @TableField("experiment_number")
    private String experimentNumber;

    @ApiModelProperty(value = "标本编号 oid", example = "SPECIMEN001")
    @TableField("specimen_number_oid")
    private String specimenNumberOid;

    @ApiModelProperty(value = "耳聋报告的地址", example = "/reports/deafness/2026/report_001.pdf")
    @TableField("deafness_report_address")
    private String deafnessReportAddress;

    @ApiModelProperty(value = "主管大夫", example = "李医生")
    @TableField("director_doctor")
    private String directorDoctor;

    @ApiModelProperty(value = "新生儿 sma 基因筛查报告路径", example = "/reports/sma/neonatal/report_001.pdf")
    @TableField("baby_sma_report_address")
    private String babySmaReportAddress;

    @ApiModelProperty(value = "产前 sma 基因筛查报告路径", example = "/reports/sma/prenatal/report_001.pdf")
    @TableField("prenatal_sma_report_address")
    private String prenatalSmaReportAddress;

    @ApiModelProperty(value = "创建时间", example = "2026-03-10 10:00:00")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间", example = "2026-03-10 15:30:00")
    @TableField("update_time")
    private LocalDateTime updateTime;
}
