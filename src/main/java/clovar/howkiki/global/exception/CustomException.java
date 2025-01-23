package clovar.howkiki.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException{

    private ErrorCode errorCode;  // 미리 정의한 에러 코드
    private String requestPath;  // 에러 발생시 사용자의 요청 url

}
