package org.xinrui.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xinrui.dto.ApiResponse;
import org.xinrui.dto.SampleDto;
import org.xinrui.service.SampleService;

import javax.validation.constraints.NotBlank;

/**
 * Halos请求样本信息控制器类
 * 该类用于处理HTTP请求，并返回相应的响应结果
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/his/V3/sample")
public class SampleController {

    @Autowired
    private SampleService requestSampleService;

    /**
     * Halos请求样本信息接口
     * GET /his/V3/sample/get/{oldSampleNum}
     */
    @GetMapping("/get/{oldSampleNum}")
    public ApiResponse<SampleDto> getSampleInfo(
            @PathVariable @NotBlank(message = "原样本编号不能为空") String oldSampleNum) {
        SampleDto result = requestSampleService.getSampleInfo(oldSampleNum);
        return ApiResponse.success(result);

    }
}
