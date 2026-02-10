package org.xinrui.dto.detectionresult.nested;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * DetectionResultDto的嵌套类定义
 * 通用疾病信息类
 */
@Data
public class DiseaseInfo {
    @ApiModelProperty(value = "区带信息", example = "del(1p36.33-p35.3,28.39M)")
    private String cytoband;

    @ApiModelProperty(value = "染色体[1-23]", example = "1")
    private int chr;

    @ApiModelProperty(value = "cnv缺失重复", example = "del(1p34.3-p22.2,51.80M)")
    private String delDup;

    @ApiModelProperty(value = "位点信息", example = "del(1:580554-28967439)")
    private String site;

    @ApiModelProperty(value = "异常类型", example = "del")
    private String cnvType;

    @ApiModelProperty(value = "片段大小", example = "28.39M")
    private String cnvSize;

    @ApiModelProperty(value = "疾病名", example = "染色体1q41-q42缺失综合征")
    private String disease;

    @ApiModelProperty(value = "疾病描述")
    private String detail;

    @ApiModelProperty(value = "表型")
    private String diseaseDetail;
}

