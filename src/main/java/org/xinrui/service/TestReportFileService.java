package org.xinrui.service;

import org.springframework.web.multipart.MultipartFile;
import org.xinrui.dto.ApiResponse;

public interface TestReportFileService {
    /**
     * 接收PDF报告文件
     *
     * @param file
     * @return
     */
    boolean receivePdfReport(MultipartFile file);


    /**
     * 接收WORD报告文件
     *
     * @param file
     * @return
     */
    boolean receiveWordReport(MultipartFile file);
}
