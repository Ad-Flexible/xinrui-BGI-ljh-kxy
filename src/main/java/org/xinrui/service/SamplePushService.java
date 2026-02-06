package org.xinrui.service;

import org.xinrui.dto.RequestModifyDTO;

public interface SamplePushService {
    /**
     * 推送样本信息到Halos系统
     * @param //request 样本数据（必填字段需校验）
     * @return true-推送成功
     * @throws //HalosApiException 推送失败时抛出（含Halos返回的错误码）
     */
    boolean pushSampleToHalos(RequestModifyDTO requestModifyDTO);
}