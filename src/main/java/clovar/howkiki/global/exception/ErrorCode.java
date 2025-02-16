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
    FAILED_TO_SCHEDULE_NEW_ORDER_NOTICE(CONFLICT, "일정 시간 후 새로운 주문 도착 알림에 문제가 발생했습니다."),
    ORDER_DETAIL_EMPTY(BAD_REQUEST, "주문 항목이 비어있습니다."),
    MENU_NOT_FOUND(NOT_FOUND, "해당 메뉴를 찾을 수 없습니다."),
    MENU_NOT_FOR_THIS_STORE(BAD_REQUEST, "해당 가게의 메뉴가 아닌 메뉴가 포함되어 있습니다."),
    INVALID_ORDER_STATUS(BAD_REQUEST, "잘못된 상태 값입니다."),
    ORDER_CANNOT_BE_CANCELLED(FORBIDDEN, "취소 할 수 없는 주문 상태입니다."),
    INVALID_STORE_ID(BAD_REQUEST, "잘못된 가게Id입니다."),
    INVALID_EXPECTED_PREP_MIN(BAD_REQUEST, "처리할 수 없는 예상 시간입니다."),
    ORDER_STATUS_CANNOT_BE_ACCEPTED(BAD_REQUEST, "수락할 수 없는 상태의 주문입니다."),

    // notice
    SESSION_TOKEN_EMPTY(BAD_REQUEST, "세션토큰이 존재하지 않습니다."),
    SSE_EMITTER_NOT_FOUND(BAD_REQUEST, "해당 세션 토큰의 SseEmitter를 찾을 수 없습니다. - SSE 연결 불가"),
    SESSION_TOKEN_NOT_VALID(NOT_FOUND, "유효하지 않은 세션토큰입니다."),
    FAILED_TO_SEND_DUMMY_NOTICE(BAD_REQUEST, "sse 연결 후 첫 더미 메시지 전송에 실패했습니다."),
    FAILED_TO_SEND_NOTICE(BAD_REQUEST, "알림 전송에 실패했습니다."),
    SSE_EMITTER_EMPTY(BAD_REQUEST, "SseEmitter가 존재하지 않습니다."),
    ORDER_NOT_FOUND(NOT_FOUND, "요청자의 최근 주문이 존재하지 않습니다."),

    // suggestion
    SUGGESTION_ID_NOT_FOUND(NOT_FOUND, "해당 Id의 건의사항을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
