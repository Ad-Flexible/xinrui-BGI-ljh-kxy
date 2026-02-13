package org.xinrui.dto.testresult.nested;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * lane质控信息
 * PushResultRequestDTO的嵌套类定义
 */
@Data
public class LaneQcDto {
    @ApiModelProperty(value = "lane质控", allowableValues = "通过,不通过")
    private String laneQcResult;

    @ApiModelProperty(value = "质控失败原因")
    private String laneQcReason;

    //todo 用double还是String
    @ApiModelProperty(value = "原始数据量均值(M)", example = "7.322")
    private Double readsNum;

    //todo 用double还是String
    @ApiModelProperty(value = "Fail_Rate(%)", example = "40.274")
    private Double failSampleRate;

    //todo 用double还是String
    @ApiModelProperty(value = "GC_mean(%)", example = "40.274")
    private Double laneGc;

    //todo 用double还是String
    @ApiModelProperty(value = "总产量(M)", example = "355.33")
    private Double totalReads;

    //todo 用double还是String
    @ApiModelProperty(value = "Q20(%)", example = "97.503")
    private Double q20;

    //todo 用double还是String
    @ApiModelProperty(value = "比对率(%)", example = "73.755")
    private Double mapRate;

    //todo 用double还是String
    @ApiModelProperty(value = "重复率(%)", example = "2.671")
    private Double duplicateRate;

    //todo 用double还是String
    @ApiModelProperty(value = "拆分率(%)", example = "75.262")
    private Double totalDecodeRate;

    //todo 用double还是String
    @ApiModelProperty(value = "Dim_Rate(%)", example = "40.274")
    private Double dimRate;
}
