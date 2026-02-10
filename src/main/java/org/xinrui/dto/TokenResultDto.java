package org.xinrui.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TokenResultDto implements Serializable {
    private Long expirationTime; // 毫秒时间戳
    private String token;
}
