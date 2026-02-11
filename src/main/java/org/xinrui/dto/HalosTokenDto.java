package org.xinrui.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;

/*
 * 预备删除
 */

@Data
public class HalosTokenDto implements Serializable {
    @NotBlank(message = "appId不能为空")
    @ApiModelProperty(value = "Halos提供的appId", required = true)
    private String appId;

    @NotBlank(message = "appKey不能为空")
    @ApiModelProperty(value = "Halos提供的appKey", required = true)
    private String appKey;
}
