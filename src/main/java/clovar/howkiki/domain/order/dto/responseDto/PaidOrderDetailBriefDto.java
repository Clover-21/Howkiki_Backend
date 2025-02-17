package clovar.howkiki.domain.order.dto.responseDto;

import clovar.howkiki.domain.order.entity.Order;
import clovar.howkiki.domain.order.entity.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaidOrderDetailBriefDto {
    private Long orderId;
    private OrderStatus status;
    private String userSessionToken;

    public PaidOrderDetailBriefDto(Long orderId, OrderStatus status, String userSessionToken) {
        this.orderId = orderId;
        this.status = status;
        this.userSessionToken = userSessionToken;
    }

    public static PaidOrderDetailBriefDto from(Order order) {
        return new PaidOrderDetailBriefDto(
                order.getOrderId(),
                order.getStatus(),
                order.getSessionToken()
        );
    }
}