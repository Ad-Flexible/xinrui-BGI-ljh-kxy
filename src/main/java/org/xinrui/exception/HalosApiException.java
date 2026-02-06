package org.xinrui.exception;
import lombok.Getter;

@Getter
public class HalosApiException extends RuntimeException {
    private final Integer halosCode; // Halos返回的业务错误码

    public HalosApiException(Integer halosCode, String message) {
        super(message);
        this.halosCode = halosCode;
    }

    public HalosApiException(String message) {
        this(-1, message);
    }
}