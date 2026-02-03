package org.xinrui.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xinrui.dto.ApiResponse;
import org.xinrui.dto.PushResultRequestDTO;
import org.xinrui.service.PushResultService;

import javax.validation.Valid;


/**
 * Halos推送结果数据控制器
 * 用于处理结果推送相关的HTTP请求
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/his/V3/sample")
public class PushResultController {

    @Autowired
    private PushResultService pushResultService;

    /**
     * Halos推送JSON结果数据接口
     * POST /his/V3/sample/pushResult
     */
    @PostMapping("/pushResult")
    public ApiResponse<Boolean> pushResult(@Valid @RequestBody PushResultRequestDTO requestDTO) {

        try {
            boolean success = pushResultService.handlePushResult(requestDTO);
            return success ?
                    ApiResponse.success(true) :
                    ApiResponse.fail("结果处理失败");
        } catch (Exception e) {
            log.error("推送结果处理异常，样本编号: {}", requestDTO.getSampleId(), e);
            return ApiResponse.fail(-1, e.getMessage());
        }
    }
}
