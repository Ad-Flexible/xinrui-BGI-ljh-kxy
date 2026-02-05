package org.xinrui.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
public class FileUtil {

    /**
     * 文件基础校验（避免空文件/异常数据）
     */
    public static void validateFile(MultipartFile file, String fileType) {
        if (file == null || file.isEmpty()) {
            String msg = String.format("%s报告文件为空或未上传", fileType);
            log.warn("❌ {}", msg);
            throw new IllegalArgumentException(msg);
        }
    }
}
