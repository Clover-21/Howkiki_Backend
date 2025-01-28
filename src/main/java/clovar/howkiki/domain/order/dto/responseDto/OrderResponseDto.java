package clovar.howkiki.domain.order.dto.responseDto;

import clovar.howkiki.domain.order.entity.Order;
import clovar.howkiki.domain.order.entity.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

// 주문 생성, 목록 전체 조회, 상세 조회시 응답dto
@Getter
@NoArgsConstructor
public class OrderResponseDto<T> {

    private Long orderId;
    private Boolean isTakeOut;
    private Long tableNumber;
    private Long orderPrice;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<T> orderDetail;  // 제너릭 타입 도입

    public OrderResponseDto(Long orderId, Boolean isTakeOut, Long tableNumber, Long orderPrice, OrderStatus status, LocalDateTime createdAt, LocalDateTime modifiedAt, List<T> orderDetail) {
        this.orderId = orderId;
        this.isTakeOut = isTakeOut;
        this.tableNumber = tableNumber;
        this.orderPrice = orderPrice;
        this.status = status;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.orderDetail = orderDetail;
    }

    // DTO 변환 from 메서드
    public static <T> OrderResponseDto<T> from (Order order, List<T> orderDetail){
        return new OrderResponseDto<T>(
                order.getOrderId(),
                order.getIsTakeOut(),
                order.getTableNumber(),
                order.getOrderPrice(),
                order.getStatus(),
                order.getCreatedAt(),
                order.getModifiedAt(),
                orderDetail
        );
    }


}
