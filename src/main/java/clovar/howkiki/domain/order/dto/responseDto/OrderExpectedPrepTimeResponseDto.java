package clovar.howkiki.domain.order.dto.responseDto;

import clovar.howkiki.domain.order.entity.Order;
import clovar.howkiki.domain.order.entity.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class OrderExpectedPrepTimeResponseDto {
    private Long orderId;
    private LocalDateTime expectedPrepTime;
    private Boolean isTakeOut;
    private Long tableNumber;
    private OrderStatus status;

    public OrderExpectedPrepTimeResponseDto(Long orderId, LocalDateTime expectedPrepTime, Boolean isTakeOut, Long tableNumber, OrderStatus status) {
        this.orderId = orderId;
        this.expectedPrepTime = expectedPrepTime;
        this.isTakeOut = isTakeOut;
        this.tableNumber = tableNumber;
        this.status = status;
    }

    public static OrderExpectedPrepTimeResponseDto from (Order order){
        return new OrderExpectedPrepTimeResponseDto(
                order.getOrderId(),
                order.getExpectedPrepTime(),
                order.getIsTakeOut(),
                order.getTableNumber(),
                order.getStatus()
        );
    }
}
