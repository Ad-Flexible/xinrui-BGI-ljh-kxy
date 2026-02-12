package org.xinrui.dto.testresult.nested;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 样本质控信息
 * PushResultRequestDTO的嵌套类定义
 */
@Data
public  class SampleQcDto {
    //todo 用double还是String
    @ApiModelProperty(value = "样本GC含量(%)", example = "40.45")
    private Double sampleGc;

    //todo 用double还是String
    @ApiModelProperty(value = "有效数据量(M)", example = "4.631")
    private Double uniqueReads;

    //todo 用double还是String
    @ApiModelProperty(value = "原始数据量(M)", example = "6.619")
    private Double readsNum;

    //todo 用double还是String
    @ApiModelProperty(value = "重复率(%)", example = "1.16")
    private Double duplicationRate;

    //todo 用double还是String
    @ApiModelProperty(value = "比对率(%)", example = "77.054")
    private Double mapRate;
}
