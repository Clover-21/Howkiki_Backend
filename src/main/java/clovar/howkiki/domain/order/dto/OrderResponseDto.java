package clovar.howkiki.domain.order.dto;


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

    private Long tableNumber;

    private Long orderPrice;

    private OrderStatus status;

    private LocalDateTime expectedPrepTime;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private List<OrderMenuListDetailDto> menuList;

    public OrderResponseDto(Long orderId, Long tableNumber, Long orderPrice, OrderStatus status, LocalDateTime expectedPrepTime, LocalDateTime createdAt, LocalDateTime modifiedAt, List<OrderMenuListDetailDto> menuList) {
        this.orderId = orderId;
        this.tableNumber = tableNumber;
        this.orderPrice = orderPrice;
        this.status = status;
        this.expectedPrepTime = expectedPrepTime;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.menuList = menuList;
    }

    public static OrderResponseDto from (Order order, List<OrderMenuListDetailDto> menuList){
        return new OrderResponseDto(
                order.getId(),
                order.getTableNumber(),
                order.getOrderPrice(),
                order.getStatus(),
                order.getExpectedPrepTime(),
                order.getCreatedAt(),
                order.getModifiedAt(),
                menuList
        );
    }


}
