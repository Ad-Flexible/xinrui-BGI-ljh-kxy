package org.xinrui.dto.detectionresult.nested;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * DetectionResultDto的嵌套类定义
 */
@Data
public class DescCenterNegativeItem {

    @ApiModelProperty(value = "疾病名")
    @JsonProperty("diseaseNameCn")
    private String diseaseNameCn;
}
