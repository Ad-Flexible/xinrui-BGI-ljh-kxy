package org.xinrui.dto.detectionresult.nested;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 染色体异常筛查结果项
 *DetectionResultDto的嵌套类定义
 */
@Data
public class ChromosomeAbnormality {

    @ApiModelProperty(value = "疾病名")
    @JsonProperty("diseaseNameCn")
    private String diseaseNameCn;

    @ApiModelProperty(value = "异常类型")
    @JsonProperty("type")
    private String type;

    @ApiModelProperty(value = "片段大小")
    @JsonProperty("size")
    private String size;

    @ApiModelProperty(value = "异常区带")
    @JsonProperty("area")
    private String area;

    @ApiModelProperty(value = "异常起止点")
    @JsonProperty("pos")
    private String pos;

    @ApiModelProperty(value = "风险提示")
    @JsonProperty("riskText")
    private String riskText;
}
