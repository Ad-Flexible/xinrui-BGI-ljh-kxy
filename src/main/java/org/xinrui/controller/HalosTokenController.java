package org.xinrui.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xinrui.dto.ApiResponse;
import org.xinrui.exception.HalosApiException;
import org.xinrui.service.TokenService;

@Slf4j
@RestController
//@RequestMapping("/his/V3/HalosToken")测试时使用
@RequestMapping("/his/open/app")
public class HalosTokenController {

    @Autowired
    private TokenService tokenService;

    /**
     * 获取有效Halos访问Token（自动刷新机制）
     * 用途：HIS系统内部测试/运维使用
     * 注意：业务流程中无需调用此接口，SamplePushService会自动获取有效Token
     *
     * @return ApiResponse<String>
     * retCode: 0=成功, -1=失败
     * result: 有效Token字符串
     */
    @GetMapping("/current")
    public ApiResponse<String> getCurrentToken() {
        String token = tokenService.getValidToken();
        log.info("Token获取成功（测试接口）");
        return ApiResponse.success(token);
    }

    /**
     * 强制刷新Token（运维场景）
     */
    @PostMapping("/refresh")
    public ApiResponse<Void> refreshToken() {
        tokenService.refreshToken();
        log.info("Token已强制刷新");
        return ApiResponse.success();
    }

    @PostMapping("/token")
    public ApiResponse<String> getToken() {
        String token = tokenService.getValidToken();
        return ApiResponse.success(token);
    }
}
