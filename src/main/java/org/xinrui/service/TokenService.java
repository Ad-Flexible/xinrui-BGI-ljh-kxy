package org.xinrui.service;

public interface TokenService {
    /**
     * 获取有效Halos token（自动缓存+刷新）
     * @return 有效token字符串
     * @throws //HalosApiException 调用失败时抛出
     * */
    String getValidToken();

    /**
     * 强制刷新token（运维场景使用）
     */
    void refreshToken();
}
