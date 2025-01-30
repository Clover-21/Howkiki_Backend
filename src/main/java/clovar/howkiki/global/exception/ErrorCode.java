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
    FAILED_TO_SCHEDULE_ORDER_STATUS(CONFLICT, "일정 시간 후 상태 변경 예약에 문제가 발생했습니다."),
    ORDER_DETAIL_EMPTY(BAD_REQUEST, "주문 항목이 비어있습니다."),
    MENU_NOT_FOUND(NOT_FOUND, "해당 메뉴를 찾을 수 없습니다."),
    MENU_NOT_FOR_THIS_STORE(BAD_REQUEST, "해당 가게의 메뉴가 아닌 메뉴가 포함되어 있습니다."),
    INVALID_ORDER_STATUS(BAD_REQUEST, "잘못된 상태 값입니다."),
    ORDER_CANNOT_BE_CANCELLED(FORBIDDEN, "취소 할 수 없는 주문 상태입니다."),
    INVALID_STORE_ID(BAD_REQUEST, "잘못된 가게Id입니다."),
    INVALID_EXPECTED_PREP_MIN(BAD_REQUEST, "처리할 수 없는 예상 시간입니다."),
    ORDER_STATUS_CANNOT_BE_ACCEPTED(BAD_REQUEST, "수락할 수 없는 상태의 주문입니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
