package org.xinrui.service;

import org.xinrui.dto.detectionresult.DetectionResultDto;

/**
 * Halos推送结果数据服务接口
 * 该接口定义了处理推送结果相关的方法
 */
public interface DetectionResultService {

    /**
     * 处理Halos推送的检测结果
     * @param requestDTO 推送数据
     * @return 处理结果（true-成功 false-失败）
     */
    boolean handlePushResult(DetectionResultDto requestDTO);
}
