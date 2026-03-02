package org.xinrui.dto.testResult.nested;
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

    @ApiModelProperty(value = "原始数据量均值(M)", example = "7.322")
    private Double readsNum;

    @ApiModelProperty(value = "Fail_Rate(%)", example = "40.274")
    private Double failSampleRate;

    @ApiModelProperty(value = "GC_mean(%)", example = "40.274")
    private Double laneGc;

    @ApiModelProperty(value = "总产量(M)", example = "355.33")
    private Double totalReads;

    @ApiModelProperty(value = "Q20(%)", example = "97.503")
    private Double q20;

    @ApiModelProperty(value = "比对率(%)", example = "73.755")
    private Double mapRate;

    @ApiModelProperty(value = "重复率(%)", example = "2.671")
    private Double duplicateRate;

    @ApiModelProperty(value = "拆分率(%)", example = "75.262")
    private Double totalDecodeRate;

    @ApiModelProperty(value = "Dim_Rate(%)", example = "40.274")
    private Double dimRate;
}
