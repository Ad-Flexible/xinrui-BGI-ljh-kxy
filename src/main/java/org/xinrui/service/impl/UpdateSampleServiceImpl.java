package org.xinrui.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.xinrui.dto.ApiResponse;
import org.xinrui.dto.SampleModifyDto;
import org.xinrui.exception.HalosApiException;
import org.xinrui.service.UpdateSampleService;
import org.xinrui.service.TokenService;

import org.springframework.http.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/*
 * 预备删除
 */

/**
 * 示例推送服务实现类
 * 实现了SamplePushService接口，负责将样本数据推送到Halos系统
 */
@Slf4j
@Service
public class UpdateSampleServiceImpl implements UpdateSampleService {

    // 推送样本到Halos的API URL配置
    @Value("${halos.api.push-sample-url}")
    private String pushSampleUrl;

    // 鉴权方式: fixed(固定鉴权-默认) / header(请求头鉴权)
    @Value("${halos.auth.type:fixed}")
    private String authType;

    // 超时配置（毫秒）
    @Value("${halos.api.connect-timeout:3000}")
    private int connectTimeout;

    @Value("${halos.api.read-timeout:5000}")
    private int readTimeout;

    private final RestTemplate restTemplate;
    private final TokenService tokenService;

    public UpdateSampleServiceImpl(RestTemplate restTemplate, TokenService tokenService) {
        this.restTemplate = restTemplate;
        this.tokenService = tokenService;
    }

    @Override
    public boolean pushSampleToHalos(@Valid @NotNull SampleModifyDto request) {
        String sampleId = request.getSampleId();
        String oldSampleNum = StringUtils.hasText(request.getOldSampleNum())
                ? request.getOldSampleNum() : "N/A";

        log.info("开始推送样本到Halos | 样本编号: {}, 原样本编号: {}", sampleId, oldSampleNum);

        try {
            // 1. 获取有效token
            String token = tokenService.getValidToken();

            // 2. 构建请求（动态选择鉴权方式）
            //todo 鉴权方式采用header是否会较好
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            String finalUrl = buildRequestUrl(token, headers);

            HttpEntity<SampleModifyDto> entity = new HttpEntity<>(request, headers);

            // 3. 调用Halos接口（带超时控制）
            ResponseEntity<ApiResponse> response =
                    restTemplate.postForEntity(finalUrl, entity, ApiResponse.class);

            // 4. 处理响应
            return handlePushResponse(response, sampleId, oldSampleNum);

        } catch (HalosApiException e) {
            log.error("推送样本业务异常 | 样本: {}, 原样本: {}, 错误码: {}, 信息: {}",
                    sampleId, oldSampleNum, e.getHalosCode(), e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("推送样本系统异常 | 样本: " + sampleId, e);
            throw new HalosApiException(-1, "推送样本失败: " + e.getMessage());
        }
    }

    /**
     * 构建请求URL和Headers（根据鉴权方式）
     */
    private String buildRequestUrl(String token, HttpHeaders headers) {
        if ("fixed".equalsIgnoreCase(authType)) {
            // 固定鉴权：token放在Query String
            log.debug("使用固定鉴权方式，token将附加到URL");
            return String.format("%s?token=%s", pushSampleUrl, token);
        } else {
            // Header鉴权：token放在请求头
            log.debug("使用Header鉴权方式，token将放在请求头");
            headers.set("token", token);
            return pushSampleUrl;
        }
    }

    /**
     * 处理Halos推送响应
     * 该方法用于处理从Halos API接收到的推送响应，并根据响应状态和业务码进行相应的处理。
     *
     * @param response ResponseEntity对象，包含Halos API的响应信息
     * @param sampleId 当前样本ID
     * @param oldSampleNum 原样本编号
     * @return boolean 如果推送成功返回true
     * @throws HalosApiException 当HTTP状态码不是OK或响应体为null时抛出异常
     * @throws HalosApiException 当Halos返回业务错误码时抛出异常
     */
    private boolean handlePushResponse(
            // HTTP响应实体，包含响应状态码和响应体
            ResponseEntity<ApiResponse> response,
            // 当前样本的唯一标识符
            String sampleId,
            // 原始样本的编号
            String oldSampleNum) {

        // 检查HTTP响应状态码和响应体是否有效
        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            // 构造错误信息，包含HTTP状态码
            String msg = String.format("Halos返回HTTP异常 | 状态码: %s", response.getStatusCode());
            // 记录错误日志
            log.error(msg);
            // 抛出Halos API异常，错误码为-1
            throw new HalosApiException(-1, msg);
        }

        // 获取响应体数据
        ApiResponse halosResp = response.getBody();
        // 检查业务返回码是否为0（成功）
        if (0 == halosResp.getRetCode()) {
            // 记录成功日志，包含样本ID和原样本编号
            log.info("推送样本成功 | 样本: {}, 原样本: {}", sampleId, oldSampleNum);
            return true;
        }

        // 处理Halos业务错误码（参考文档6.1.5响应码）
        // 构造错误信息，包含业务错误码和错误信息
        String errorMsg = String.format("Halos返回业务错误[%s]: %s",
                halosResp.getRetCode(), halosResp.getRetInfo());
        // 记录错误日志，包含样本ID、原样本编号和错误信息
        log.error("推送样本失败 | 样本: {}, 原样本: {}, {}",
                sampleId, oldSampleNum, errorMsg);

        // 抛出Halos API异常，错误码为Halos返回的业务错误码
        throw new HalosApiException(halosResp.getRetCode(), errorMsg);
    }
}
