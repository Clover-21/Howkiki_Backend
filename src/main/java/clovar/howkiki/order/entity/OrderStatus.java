package clovar.howkiki.order.entity;

public enum OrderStatus {
    PENDING,  // 접수 대기
    PROCESSING,  // 조리중
    COMPLETED,  // 조리 완료
    CANCELED  // 취소됨
}
