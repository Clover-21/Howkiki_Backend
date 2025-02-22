package clovar.howkiki.domain.order.dto.responseDto;

import clovar.howkiki.domain.order.entity.Order;
import clovar.howkiki.domain.order.entity.OrderStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

// 주문자의 주문 내역 조회시 OrderList의 order DTO
@Getter
@NoArgsConstructor
public class UserOrderDto<T> {

    private Long orderId;
    private Long orderPrice;
    private OrderStatus status;
    private List<T> orderDetail;  // 제너릭 타입 도입

    public UserOrderDto(Long orderId, Long orderPrice, OrderStatus status, List<T> orderDetail) {
        this.orderId = orderId;
        this.orderPrice = orderPrice;
        this.status = status;
        this.orderDetail = orderDetail;
    }

    // DTO 변환 from 메서드
    public static <T> UserOrderDto<T> from(Order order, List<T> orderDetail){
        return new UserOrderDto<T>(
                order.getOrderId(),
                order.getOrderPrice(),
                order.getStatus(),
                orderDetail
        );
    }

}
