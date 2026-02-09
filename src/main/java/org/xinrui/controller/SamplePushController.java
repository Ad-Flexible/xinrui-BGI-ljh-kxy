package org.xinrui.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xinrui.dto.ApiResponse;
import org.xinrui.dto.RequestModifyDTO;
import org.xinrui.exception.HalosApiException;
import org.xinrui.service.SamplePushService;

import javax.validation.Valid;

@Slf4j
@Validated
@RestController
@RequestMapping("/his/V3/sample")
public class SamplePushController {

    @Autowired
    private SamplePushService samplePushService;

    /**
     * 推送样本信息至Halos系统
     *
     * @param request 样本信息（字段要求严格遵循Halos接口文档6.1.4节）
     *                必填字段示例：productNo, sampleId, sampleType, collectDate等
     * @return ApiResponse<Boolean>
     * retCode: 0=推送成功, -1=推送失败
     * result: true（仅成功时返回）
     * retInfo: 详细错误信息（含Halos返回的业务错误码）
     *
     */
    @PostMapping("/push")
    public ApiResponse<Boolean> pushSample(@Valid @RequestBody RequestModifyDTO request) {
        boolean success = samplePushService.pushSampleToHalos(request);
        log.info("样本推送成功 | 样本编号: {}", request.getSampleId());
        return ApiResponse.success();
    }
}
