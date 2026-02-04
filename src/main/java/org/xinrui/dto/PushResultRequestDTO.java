package org.xinrui.dto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;


@Data
public class PushResultRequestDTO {

    @NotBlank(message = "样本编号不能为空")
    @ApiModelProperty(value = "样本编号", required = true, example = "17B1234567")
    private String sampleId;

    @NotBlank(message = "子文库编号不能为空")
    @ApiModelProperty(value = "子文库号", required = true, example = "17B1234567-1-13")
    private String poolingSubId;

    @NotBlank(message = "芯片编号不能为空")
    @ApiModelProperty(value = "芯片号", required = true, example = "CL100007551")
    private String slideId;

    @NotBlank(message = "Lane ID不能为空")
    @ApiModelProperty(value = "Lane ID", required = true, example = "1")
    private String laneId;

    @NotBlank(message = "DNB ID不能为空")
    @ApiModelProperty(value = "DNB ID", required = true, example = "SZDNB018")
    private String dnbId;

    //todo 在此文档其他处为int类型
    @NotBlank(message = "样本类型不能为空")
    @ApiModelProperty(value = "样本类型", required = true, allowableValues = "全血,血浆,DNA")
    private String sampleType;

    //todo 在此文档其他处为int类型
    @NotBlank(message = "运输条件不能为空")
    @ApiModelProperty(value = "运输条件", required = true, allowableValues = "4℃,6~35℃,dry ice")
    private String shipmentCondition;

    @NotBlank(message = "取样日期不能为空")
    @ApiModelProperty(value = "取样日期", required = true, example = "2017-06-14 18:00")
    private String collectDate;

    @NotBlank(message = "收样日期不能为空")
    @ApiModelProperty(value = "收样日期", required = true, example = "2017-06-14 18:00")
    private String receivedDate;

    @NotBlank(message = "孕周不能为空")
    @ApiModelProperty(value = "孕周（周,天）", required = true, example = "30,6")
    private String gestationalWeeks;

    //todo 在此文档其他处为int类型
    @NotBlank(message = "胎儿类型不能为空")
    @ApiModelProperty(value = "胎儿类型", required = true, allowableValues = "单胎,双胎")
    private String fetusType;

    //todo 在此文档其他处为int类型
    @NotBlank(message = "管路类型不能为空")
    @ApiModelProperty(value = "管道类型", required = true, allowableValues = "Streck管,EDTA管,K管,G管,X管")
    private String tubeType;

    @ApiModelProperty(value = "是否需要附加报告", allowableValues = "1,2", notes = "1=是,2=否,0=空")
    private int additionalReportFlag;

    @NotBlank(message = "原样本编号不能为空")
    @ApiModelProperty(value = "原样本编号", required = true, example = "2342343SF")
    private String oldSampleNum;

    @ApiModelProperty(value = "孕妇姓名", example = "张三")
    private String patientName;

    @ApiModelProperty(value = "身份证号", example = "110224199901012345")
    private String patientIdCard;

    @ApiModelProperty(value = "年龄", example = "32")
    private int patientAge;

    @ApiModelProperty(value = "门诊号", example = "1234")
    private String clinicNum;

    @ApiModelProperty(value = "送检医院", example = "中国XX医院")
    private String hospitalName;

    @ApiModelProperty(value = "送检医生", example = "张医生")
    private String doctorName;

    @ApiModelProperty(value = "联系地址", example = "北京市西城区XX街道XX号")
    private String patientAddress;

    @ApiModelProperty(value = "手机号码", example = "15012341234")
    private String patientMobile;

    @ApiModelProperty(value = "末次月经", example = "2017-06-14 18:00")
    private String lastMenstrualPeriod;

    //todo 在此文档其他处为int类型
    @ApiModelProperty(value = "绒毛膜", allowableValues = "DC,DA,MC,DCDA,CMA,MCDA")
    private String chorion;

    //todo 在此文档其他处为int类型
    @ApiModelProperty(value = "唐筛结果", allowableValues = "13-三体，低风险,13-三体，中风险,13-三体，高风险,18-三体，低风险,18-三体，中风险,18-三体，高风险,21-三体，低风险,21-三体，中风险,21-三体，高风险")
    private String downScreening;

    //todo 在此文档其他处为int类型
    @ApiModelProperty(value = "B超检查结果", allowableValues = "正常单活胎,正常非单胎,异常单活胎")
    private String bUltrasonography;

    //todo 在此文档其他处为int类型
    @ApiModelProperty(value = "是否IVF", allowableValues = "是,否")
    private String ivfFlag;

    //todo 在此文档其他处为int类型
    @ApiModelProperty(value = "受孕方式", allowableValues = "自然受孕,IUI,IVF-ET")
    private String conceptionMethod;

    @ApiModelProperty(value = "分娩史-孕次", example = "2")
    private int bhGravidity;

    @ApiModelProperty(value = "分娩史-产次", example = "2")
    private int bhParity;

    @ApiModelProperty(value = "分娩史-其它", example = "曾经多次流产")
    private String bhOther;

    @ApiModelProperty(value = "羊水穿刺", example = "羊水穿刺结果XXX")
    private String amniocentesis;

    @ApiModelProperty(value = "既往史", example = "XXX")
    private String illnessHistoryPast;

    @ApiModelProperty(value = "现病史", example = "现患有XXX病")
    private String illnessHistoryPresent;

    @ApiModelProperty(value = "过敏史", example = "海鲜过敏")
    private String illnessHistoryAllergy;

    @ApiModelProperty(value = "家族遗传病史", example = "XXX遗传病")
    private String illnessHistoryGenetic;

    @ApiModelProperty(value = "备注", example = "XXX注意")
    private String patientRemark;

    @ApiModelProperty(value = "样本质控", allowableValues = "通过,不通过")
    private String sampleQcResult;

    @ApiModelProperty(value = "操作建议", allowableValues = "合格,重上机,重建库,重取样,退费")
    private String recommendedOperation;

    @ApiModelProperty(value = "医生意见", allowableValues = "合格,重上机,重建库,重取样,退费")
    private String doctorOpinion;

    //todo 在此文档其他处为Enum类型
    @ApiModelProperty(value = "检测结果", allowableValues = "合格阴性,合格阳性,合格异常,失败")
    private String detectionResult;

    //todo 在此文档其他处为Enum类型
    @ApiModelProperty(value = "报告类型", allowableValues = "单胎报告,双胎报告,重取样,退费报告")
    private String reportType;

    //todo 用double还是String
    @ApiModelProperty(value = "Z(chr13)", example = "0.205")
    private Double zChr13;

    @ApiModelProperty(value = "Z(chr18)", example = "0.205")
    private String zChr18;

    @ApiModelProperty(value = "Z(chr21)", example = "0.205")
    private String zChr21;

    @ApiModelProperty(value = "13号染色体风险判定", allowableValues = "未检出T13,chr13偏高,chr13危险,T13高危,chr13偏少,T13,S13,nan")
    private String testChr13;

    @ApiModelProperty(value = "18号染色体风险判定", allowableValues = "未检出T18,chr18偏高,chr18危险,T18高危,chr18偏少,T18,S18,nan")
    private String testChr18;

    @ApiModelProperty(value = "21号染色体风险判定", allowableValues = "未检出T21,chr21偏高,chr21危险,T21高危,chr21偏少,T21,S21,nan")
    private String testChr21;

    @ApiModelProperty(value = "Test(性染色体)", allowableValues = "未检出性染色体数目异常、\n" +
            "XO高危（+）、\n" +
            "XXX高危（+）、\n" +
            "XXY高危（+）、\n" +
            "XYY高危（+）、\n" +
            "其他复杂异常（+）、\n" +
            "XO高危（+++）、\n" +
            "XXX高危（+++）、\n" +
            "XXY高危（+++）、\n" +
            "XYY高危（+++）、\n" +
            "其他复杂异常（+++）、\n" +
            "空格（nan）、\n" +
            "X(偏多)-M、\n" +
            "X(偏少)-M")
    private String testChrSex;

    @ApiModelProperty(value = "性染色体异常提示", example = "6.187,0.268",notes = "当Test(性染色体)有值时，取X/Y浓度，中间用英文逗号分")
    private String abnormalChrX;

    @ApiModelProperty(value = "补充", example = "单样本流程")
    private String chrN;

    //todo 用double还是String
    @ApiModelProperty(value = "胎儿浓度", example = "10.01")
    private Double fetalFraction;

    //todo 用double还是String
    @ApiModelProperty(value = "13浓度", example = "-1.359")
    private Double childLvChr13;

    //todo 用double还是String
    @ApiModelProperty(value = "18浓度", example = "-1.359")
    private Double childLvChr18;

    //todo 用double还是String
    @ApiModelProperty(value = "21浓度", example = "-1.359")
    private Double childLvChr21;

    @ApiModelProperty(value = "Test(缺失重复)", example = "dup(18p11.31p11.23,1.48M)-M")
    private String testDeletionDuplication;

    @ApiModelProperty(value = "异常染色体数", example = "1")
    private int abnormalChrNum;

    //todo 用double还是String
    @ApiModelProperty(value = "性染色体浓度差", example = "3.394")
    private Double fracAbs;

    @ApiModelProperty(value = "13号染色体风险值", example = "1/42330418")
    private String riskIndex13;

    @ApiModelProperty(value = "18号染色体风险值", example = "1/1636954")
    private String riskIndex18;

    @ApiModelProperty(value = "21号染色体风险值", example = "1/143540")
    private String riskIndex21;

    @ApiModelProperty(value = "其他常染色体", example = "chr7(HT, 10.71449, 0.22254)")
    private String testChrOther;

    @ApiModelProperty(value = "是否重复样本", example = "2")
    private int repeatCount;

    @ApiModelProperty(value = "检测时间", example = "2019-07-16")
    private String testTime;

    @ApiModelProperty(value = "报告创建时间", example = "2019-07-16")
    private String reportCreateTime;

    // 嵌套对象
    @ApiModelProperty(value = "疾病表型列表")
    private List<DiseaseItem> diseaseList;

    @ApiModelProperty(value = "其他疾病列表")
    private List<OtherDiseaseItem> otherDiseaseList;

    @ApiModelProperty(value = "样本质控信息")
    private SampleQcInfo sampleQc;

    @ApiModelProperty(value = "Lane质控信息")
    private LaneQcInfo laneQc;


}