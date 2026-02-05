package org.xinrui.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xinrui.dto.ApiResponse;
import org.xinrui.dto.RequestSampleResponseDTO;
import org.xinrui.service.RequestSampleService;

import javax.validation.constraints.NotBlank;

/**
 * Halos请求样本信息控制器类
 * 该类用于处理HTTP请求，并返回相应的响应结果
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/his/V3/sample")
public class RequestSampleController {

    @Autowired
    private RequestSampleService requestSampleService;

    /**
     * Halos请求样本信息接口
     * GET /his/V3/sample/get/{oldSampleNum}
     */
    @GetMapping("/get/{oldSampleNum}")
    public ApiResponse<RequestSampleResponseDTO> getSampleInfo(
            @PathVariable @NotBlank(message = "原样本编号不能为空") String oldSampleNum) {

        try {
            RequestSampleResponseDTO result = requestSampleService.getSampleInfo(oldSampleNum);
            return ApiResponse.success(result);
        } catch (Exception e) {
            //先使用catch捕获全部exception,相当后面的所有异常都统一处理了不受GlobalExceptionHandler处理
            log.error("查询样本信息异常，原样本编号: {}", oldSampleNum, e);
            return ApiResponse.fail(-1, "请求失败");
        }
    }
}
