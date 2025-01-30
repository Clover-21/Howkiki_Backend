package clovar.howkiki.domain.order.dto.responseDto;

import clovar.howkiki.domain.order.entity.CancelReason;
import clovar.howkiki.domain.order.entity.Order;
import clovar.howkiki.domain.order.entity.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// 주문 생성, 목록 전체 조회, 상세 조회, 수정 시 응답dto
@Getter
@NoArgsConstructor
public class OrderCancelResponseDto {

    private Long orderId;
    private Boolean isTakeOut;
    private Long tableNumber;
    private Long orderPrice;
    private OrderStatus status;
    private CancelReason cancelReason;
    private String soldOutMenu;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public OrderCancelResponseDto(Long orderId, Boolean isTakeOut, Long tableNumber, Long orderPrice, OrderStatus status, CancelReason cancelReason, String soldOutMenu, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.orderId = orderId;
        this.isTakeOut = isTakeOut;
        this.tableNumber = tableNumber;
        this.orderPrice = orderPrice;
        this.status = status;
        this.cancelReason = cancelReason;
        this.soldOutMenu = soldOutMenu;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    // DTO 변환 from 메서드
    public static OrderCancelResponseDto from(Order order){
        return new OrderCancelResponseDto(
                order.getOrderId(),
                order.getIsTakeOut(),
                order.getTableNumber(),
                order.getOrderPrice(),
                order.getStatus(),
                order.getCancelReason(),
                order.getSoldOutMenu(),
                order.getCreatedAt(),
                order.getModifiedAt()
        );
    }

}
