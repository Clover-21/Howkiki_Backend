package clovar.howkiki.domain.order.dto.responseDto;

import clovar.howkiki.domain.order.entity.Order;
import clovar.howkiki.domain.order.entity.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TableOrderDetailBriefDto {
    private Long orderId;
    private OrderStatus status;

    public TableOrderDetailBriefDto(Long orderId, OrderStatus status) {
        this.orderId = orderId;
        this.status = status;
    }

    public static TableOrderDetailBriefDto from(Order order) {
        return new TableOrderDetailBriefDto(
                order.getOrderId(),
                order.getStatus()
        );
    }
}