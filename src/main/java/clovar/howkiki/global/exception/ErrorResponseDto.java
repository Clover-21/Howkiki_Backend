package clovar.howkiki.global.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponseDto {

    private int status;
    private String error;
    private String message;
    private String requestPath;
    private LocalDateTime timestamp;

    public ErrorResponseDto(int status, String error, String message, String requestPath, LocalDateTime timestamp) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.requestPath = requestPath;
        this.timestamp = timestamp;
    }
}
