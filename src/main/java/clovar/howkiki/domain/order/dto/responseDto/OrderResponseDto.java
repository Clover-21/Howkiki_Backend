package clovar.howkiki.domain.order.dto.responseDto;


import clovar.howkiki.domain.order.entity.Order;
import clovar.howkiki.domain.order.entity.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

// 주문 생성, 주문 상세 조회시 응답dto
@Getter
@NoArgsConstructor
@Builder
public class OrderResponseDto {

    private Long orderId;
    private Boolean isTakeOut;
    private Long tableNumber;
    private Long orderPrice;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<OrderDetailDto> orderDetail;

    public OrderResponseDto(Long orderId, Boolean isTakeOut, Long tableNumber, Long orderPrice, OrderStatus status, LocalDateTime createdAt, LocalDateTime modifiedAt, List<OrderDetailDto> orderDetail) {
        this.orderId = orderId;
        this.isTakeOut = isTakeOut;
        this.tableNumber = tableNumber;
        this.orderPrice = orderPrice;
        this.status = status;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.orderDetail = orderDetail;
    }

    public static OrderResponseDto from (Order order, List<OrderDetailDto> orderDetail){
        return new OrderResponseDto(
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
