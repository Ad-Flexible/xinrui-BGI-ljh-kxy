package org.xinrui.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xinrui.dto.ApiResponse;
import org.xinrui.service.TestReportFileService;

@RestController
@RequestMapping("/api/test/report")
public class TestReportFileController {

    @Autowired
    private TestReportFileService testReportFileService;

    @PostMapping
    public ApiResponse receivePdfReport(@RequestParam("file") MultipartFile file) {
        boolean success = testReportFileService.receivePdfReport(file);
        return success ? ApiResponse.success() : ApiResponse.fail("上传失败");
    }

    @PostMapping
    public ApiResponse receiveWordReport(@RequestParam("file") MultipartFile file) {
        boolean success = testReportFileService.receiveWordReport(file);
        return success ? ApiResponse.success() : ApiResponse.fail("上传失败");
    }


}
