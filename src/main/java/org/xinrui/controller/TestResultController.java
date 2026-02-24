package org.xinrui.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xinrui.dto.ApiResponse;
import org.xinrui.dto.testResult.TestResultDto;
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
    @PostMapping("/pushPdfReport")
    public ApiResponse<Void> pushPdfReport(@RequestParam("file") MultipartFile file) {
        FileUtil.validateFile(file, "PDF");

        String filename = file.getOriginalFilename();
        //前提是获得的file名可以与样本做关联，例如文件名是sample_id那就知道是哪个样本的word文件
        log.info("✅ 接收PDF报告文件成功 | 文件名: {} | 大小: {} bytes",
                filename, file.getSize());

        // TODO: 后续扩展（当前按需求：接收到即视为成功）
        // pushResultService.saveReportFile(file, "PDF");

        return ApiResponse.success();
    }

    /**
     * 接收Halos推送的WORD报告文件（multipart/form-data）
     * POST /his/V3/sample/pushWordReport
     *
     * 约定同PDF接口
     */
    @PostMapping("/pushWordReport")
    public ApiResponse<Void> pushWordReport(@RequestParam("file") MultipartFile file) {
        FileUtil.validateFile(file, "WORD");

        String filename = file.getOriginalFilename();
        //前提是获得的file名可以与样本做关联，例如文件名是sample_id那就知道是哪个样本的word文件
        log.info("✅ 接收WORD报告文件成功 | 文件名: {} | 大小: {} bytes",
                filename, file.getSize());

        // TODO: 后续扩展
        // pushResultService.saveReportFile(file, "WORD");

        return ApiResponse.success();
    }

}
