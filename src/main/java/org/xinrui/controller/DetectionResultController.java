package org.xinrui.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xinrui.dto.ApiResponse;
import org.xinrui.dto.detectionresult.DetectionResultDto;
import org.xinrui.service.DetectionResultService;
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
public class DetectionResultController {

    @Autowired
    private DetectionResultService pushResultService;

    /**
     * Halos推送JSON结果数据接口
     * POST /his/V3/sample/pushResult
     */
    @PostMapping("/pushResult")
    public ApiResponse<Void> pushResult(@Valid @RequestBody DetectionResultDto requestDTO) {

//        try {
//            boolean success = pushResultService.handlePushResult(requestDTO);
//            return success ?
//                    ApiResponse.success() : // 调用无参 success
//                    ApiResponse.fail("请求失败");
//        } catch (Exception e) {
//            log.error("推送结果处理异常，样本编号: {}", requestDTO.getSampleId(), e);
//            return ApiResponse.fail(-1, e.getMessage());
//        }


        //目前能成功接收到都视为可以响应成功，利用@Valid去作校验若传入请求体数据不规范会被全局异常处理器捕捉并返回ApiResponse.fail
        return ApiResponse.success();
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
