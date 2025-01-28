package clovar.howkiki.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {

    // Store
    MISSING_PARAMETER(BAD_REQUEST, "필수 파라미터가 누락되었습니다."),
    STORE_NOT_FOUND(NOT_FOUND, "해당 이름의 가게를 찾을 수 없습니다."),

    // Order
    STORE_ID_NOT_FOUND(NOT_FOUND, "해당 Id의 가게를 찾을 수 없습니다."),
    ORDER_DETAIL_EMPTY(BAD_REQUEST, "주문 항목이 비어있습니다."),
    MENU_NOT_FOUND(NOT_FOUND, "해당 메뉴를 찾을 수 없습니다."),
    MENU_NOT_FOR_THIS_STORE(BAD_REQUEST, "해당 가게의 메뉴가 아닌 메뉴가 포함되어 있습니다."),
    INVALID_ORDER_STATUS(BAD_REQUEST, "잘못된 상태 값입니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
