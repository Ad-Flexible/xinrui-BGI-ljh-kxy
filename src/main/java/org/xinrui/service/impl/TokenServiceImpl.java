package org.xinrui.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.xinrui.dto.ApiResponse;
import org.xinrui.dto.TokenResultDTO;
import org.xinrui.exception.HalosApiException;
import org.xinrui.service.TokenService;
import org.xinrui.dto.HalosTokenDTO;

import javax.annotation.PostConstruct;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Token服务实现类
 * 使用@Service注解标记为Spring服务组件
 * 使用@Slf4j注解提供日志支持
 */
@Slf4j
@Service
public class TokenServiceImpl implements TokenService {

    /**
     * 从配置文件中注入token服务URL
     */
    @Value("${halos.api.token-url}")
    private String tokenUrl;

    /**
     * 从配置文件中注入应用ID
     */
    @Value("${halos.app.app-id}")
    private String appId;

    /**
     * 从配置文件中注入应用密钥
     */
    @Value("${halos.app.app-key}")
    private String appKey;

    // 提前60秒刷新，避免临界点失效
    @Value("${halos.token.refresh-advance:60000}")
    private long refreshAdvance;

    @Autowired
    private final RestTemplate restTemplate;
    private volatile String cachedToken;
    private volatile long expireTimestamp; // 毫秒时间戳
    private final ReentrantLock lock = new ReentrantLock();

/**
 * TokenServiceImpl类的构造函数
 * @param restTemplate RestTemplate实例，用于发送HTTP请求
 */
    public TokenServiceImpl(RestTemplate restTemplate) {
    // 将传入的RestTemplate实例赋值给类的成员变量
        this.restTemplate = restTemplate;
    }

/**
 * 使用@PostConstruct注解标记的方法，在依赖注入完成后自动执行
 * 该方法用于初始化TokenService，记录token刷新提前量的配置信息
 */
    @PostConstruct
    public void init() {
        log.info("TokenService初始化完成，token刷新提前量: {}ms", refreshAdvance); // 记录TokenService初始化完成信息，并输出token刷新提前量的配置值
    }

    @Override
    public String getValidToken() {
        // 双重检查锁：避免高并发下重复刷新
    // 第一次检查：在加锁前先检查，减少不必要的锁竞争
        if (isTokenValid()) {
            return cachedToken;
        }

    // 加锁，确保线程安全
        lock.lock();
        try {
        // 第二次检查：在加锁后再次检查，防止其他线程已经刷新了token
            if (isTokenValid()) {
                return cachedToken;
            }
        // 如果token无效，则刷新token
            refreshInternal();
        // 返回刷新后的token
            return cachedToken;
        } finally {
        // 确保锁一定会被释放，防止死锁
            lock.unlock();
        }
    }

    @Override
    /**
     * 刷新令牌的方法
     * 使用锁机制确保线程安全
     */
    public void refreshToken() {
        // 获取锁，确保线程安全
        lock.lock();
        try {
            // 调用内部方法进行令牌刷新
            refreshInternal();
        } finally {
            // 确保锁一定会被释放，避免死锁
            lock.unlock();
        }
    }

/**
 * 检查token是否有效的私有方法
 * 通过比较当前时间与token过期时间减去提前刷新的时间差来判断
 *
 * @return 如果token有效返回true，否则返回false
 */
    private boolean isTokenValid() {
    // 检查缓存的token是否不为null，并且当前时间是否小于token过期时间减去提前刷新的时间
        return cachedToken != null
                && System.currentTimeMillis() < (expireTimestamp - refreshAdvance);
    }

/**
 * 内部方法，用于刷新Halos的token
 * 该方法会构造请求并调用API获取新的token
 */
    private void refreshInternal() {
    // 记录开始刷新token的日志
        log.info("开始刷新Halos token...");
        try {
        // 创建HalosTokenDTO对象，用于封装请求参数
            HalosTokenDTO request = new HalosTokenDTO();
        // 设置应用ID
            request.setAppId(appId);
        // 设置应用密钥
            request.setAppKey(appKey);

        // 创建HTTP请求头，设置内容类型为JSON
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
        // 创建HTTP实体，包含请求体和请求头
            HttpEntity<HalosTokenDTO> entity = new HttpEntity<>(request, headers);

        // 发送POST请求获取token，并指定响应类型为ApiResponse
            ResponseEntity<ApiResponse> response =
                    restTemplate.postForEntity(tokenUrl, entity, ApiResponse.class);

        // 处理API响应
            handleResponse(response);
        // 记录token刷新成功的日志，包含有效期信息
            log.info("Halos token刷新成功，有效期至: {}", expireTimestamp);
        } catch (Exception e) {
        // 记录异常日志
            log.error("刷新Halos token异常", e);
        // 抛出带有错误信息的自定义异常
            throw new HalosApiException(-1, "获取token失败: " + e.getMessage());
        }
    }

/**
 * 处理Halos API的响应结果
 * @param response ResponseEntity类型的API响应对象，包含响应状态和响应体
 * @throws HalosApiException 当响应状态码不是OK、响应体为空、返回码不为0或结果为空时抛出异常
 */
    private void handleResponse(ResponseEntity<ApiResponse> response) {
    // 检查响应状态码是否为OK且响应体不为空
        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
        // 如果状态码不是OK或响应体为空，抛出异常
            throw new HalosApiException(-1, "Halos token接口返回异常，状态码: " + response.getStatusCode());
        }

    // 获取响应体中的实际数据
        ApiResponse halosResp = response.getBody();
    // 检查返回码是否为0且结果不为空
        if (0 != halosResp.getRetCode() || halosResp.getResult() == null) {
        // 构造错误信息字符串
            String errorMsg = String.format("Halos返回错误[%s]: %s",
                    halosResp.getRetCode(), halosResp.getRetInfo());
        // 记录错误日志
            log.error(errorMsg);
        // 抛出异常
            throw new HalosApiException(halosResp.getRetCode(), errorMsg);
        }

    // 从响应结果中提取TokenResultDTO对象
        TokenResultDTO tokenResult = (TokenResultDTO) halosResp.getResult();
    // 缓存获取到的token
        this.cachedToken = tokenResult.getToken();
    // 设置token的过期时间戳
        this.expireTimestamp = tokenResult.getExpirationTime();

    // 检查token是否已过期
        if (System.currentTimeMillis() >= expireTimestamp) {
        // 如果token已过期，抛出异常
            throw new HalosApiException(-1, "获取到的token已过期");
        }
    }
}
