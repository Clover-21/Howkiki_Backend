package clovar.howkiki.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice  // 전역에서 발생 할 수 있는 예외를 잡아 처리
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)  // 발생한 CustomException 예외를 잡아 아래의 메서드로 처리
    protected ResponseEntity<ErrorResponseDto> handleCustomException(CustomException e) {

        // 응답 생성
        ErrorResponseDto responseDto = ErrorResponseDto.builder()
                .status(e.getErrorCode().getStatus().value())
                .error(e.getErrorCode().getStatus().getReasonPhrase())
                .message(e.getErrorCode().getMessage())
                .requestPath(e.getRequestPath())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(e.getErrorCode().getStatus()).body(responseDto);

    }
}
