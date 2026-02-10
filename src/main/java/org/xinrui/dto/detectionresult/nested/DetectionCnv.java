package org.xinrui.dto.detectionresult.nested;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * DetectionResultDto的嵌套类定义
 * 疾病表型列表
 */
@Data
public  class DetectionCnv {

    private DiseaseInfo diseaseInfo;

    @ApiModelProperty(value = "产品套餐编号", example = "DX0558")
    private String productNo;

    @ApiModelProperty(value = "产品套餐名称", example = "NIFTY基础")
    private String productName;
}
