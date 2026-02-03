package org.xinrui.dto;

import lombok.Data;
import java.io.Serializable;

/**
 * 统一API响应封装
 */
@Data
public class ApiResponse<T> implements Serializable {

    private Integer retCode;  // 0-成功 非0-失败
    private String retInfo;   // 返回信息
    private T result;         // 业务数据

    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setRetCode(0);
        response.setRetInfo("success");
        response.setResult(data);
        return response;
    }

    public static <T> ApiResponse<T> fail(int code, String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setRetCode(code);
        response.setRetInfo(message);
        return response;
    }

    public static <T> ApiResponse<T> fail(String message) {
        return fail(-1, message);
    }
}