package clovar.howkiki.domain.order.entity;

public enum OrderStatus {
    NOT_YET_SENT, // 주문 전송 대기
    AWAITING_ACCEPTANCE,  // 접수 대기
    IN_PROGRESS,  // 조리중
    COMPLETED,  // 조리 완료
    PAID,  // 결제 완료
    USER_CANCELLED, // 사용자에 의한 취소
    ADMIN_CANCELLED   // 운영자의 의한 취소
}
