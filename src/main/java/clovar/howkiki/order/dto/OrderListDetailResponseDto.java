package clovar.howkiki.order.dto;

import clovar.howkiki.order.entity.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@Builder
public class OrderListDetailResponseDto {
    private Long orderId;
    private Long tableNumber;
    private Long orderPrice;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private List<OrderDetailDto> menuSummary;

    public OrderListDetailResponseDto(Long orderId, Long tableNumber, Long orderPrice, OrderStatus status, LocalDateTime createdAt, List<OrderDetailDto> menuSummary) {
        this.orderId = orderId;
        this.tableNumber = tableNumber;
        this.orderPrice = orderPrice;
        this.status = status;
        this.createdAt = createdAt;
        this.menuSummary = menuSummary;
    }
}
