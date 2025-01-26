package clovar.howkiki.global.exception;

import clovar.howkiki.domain.order.entity.OrderStatus;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.Arrays;

@ControllerAdvice  // 전역에서 발생 할 수 있는 예외를 잡아 처리
public class CustomExceptionHandler {


    // CustomException 에러
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

    // 메소드 파라미터 에러
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e, HttpServletRequest request) {

        String errorMessage = String.format(
                "요청한 파라미터 '%s'에 잘못된 값 '%s'이(가) 전달되었습니다. 지원하는 값: %s",
                e.getName(),
                e.getValue(),
                Arrays.toString(OrderStatus.values())
        );
        // 응답 생성
        ErrorResponseDto responseDto = ErrorResponseDto.builder()
                .status(400)
                .error(e.getErrorCode())
                .message(errorMessage)
                .requestPath(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(400).body(responseDto);

    }
}
