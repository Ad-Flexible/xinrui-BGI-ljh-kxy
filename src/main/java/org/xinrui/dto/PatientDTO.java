package org.xinrui.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PatientDTO {

    @ApiModelProperty(value = "产品套餐编号")
    private String productNo;

    @ApiModelProperty(value="产品套餐名称")
    private String productName;

    @ApiModelProperty(value="样本编号")
    private String sampleId;
}
