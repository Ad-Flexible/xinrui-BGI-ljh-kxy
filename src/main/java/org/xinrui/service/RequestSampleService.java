package org.xinrui.service;

import org.xinrui.dto.RequestSampleResponseDTO;

/**
 * RequestSampleService接口
 * 这是一个服务接口，可能用于Halos请求样本信息相关的业务逻辑
 */
public interface RequestSampleService {

    /**
     * 根据原样本编号查询样本信息
     * @param oldSampleNum 原样本编号
     * @return 样本信息DTO
     * @throws org.xinrui.exception.BusinessException 业务异常
     */
    RequestSampleResponseDTO getSampleInfo(String oldSampleNum);
}
