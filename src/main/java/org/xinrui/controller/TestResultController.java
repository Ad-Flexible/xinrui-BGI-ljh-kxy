package org.xinrui.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xinrui.dto.ApiResponse;
import org.xinrui.dto.testResult.TestResultDto;
import org.xinrui.service.TestReportFileService;
import org.xinrui.service.TestResultService;
import org.xinrui.util.FileUtil;
import javax.validation.Valid;


/**
 * Halos推送结果数据控制器
 * 用于处理结果推送相关的HTTP请求
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/his/V3/result")
public class TestResultController {

    @Autowired
    private TestResultService testResultService;

    @Autowired
    private TestReportFileService testReportFileService;

    /**
     * Halos推送JSON结果数据接口
     * POST /his/V3/sample/pushResult
     */
    @PostMapping("/pushResult")
    public ApiResponse<Void> pushResult(@Valid @RequestBody TestResultDto requestDTO) {

            boolean success = testResultService.handlePushResult(requestDTO);
            return success ?
                    ApiResponse.success() : // 调用无参 success
                    ApiResponse.fail("请求失败");


    }

    /**
     * 接收Halos推送的PDF报告文件（multipart/form-data）
     * POST /his/V3/sample/pushPdfReport
     *
     * 约定：
     * 1. Content-Type: multipart/form-data
     * 2. 文件字段名: file
     * 3. 文件名由Halos系统通过"报告命名"功能生成，HIS通过文件名匹配样本
     */
    @PostMapping(path = "/pushPdfReport")
    public ApiResponse receivePdfReport(@RequestParam("file") MultipartFile file) {
        log.info("接收PDF报告文件");
        boolean success = testReportFileService.receivePdfReport(file);
        return success ? ApiResponse.success() : ApiResponse.fail("上传失败");
    }

    /**
     * 接收Halos推送的WORD报告文件（multipart/form-data）
     * POST /his/V3/sample/pushWordReport
     *
     * 约定同PDF接口
     */
    @PostMapping(path = "/pushWordReport")
    public ApiResponse receiveWordReport(@RequestParam("file") MultipartFile file) {
        boolean success = testReportFileService.receiveWordReport(file);
        return success ? ApiResponse.success() : ApiResponse.fail("上传失败");
    }

}
