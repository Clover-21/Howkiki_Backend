package clovar.howkiki.domain.notification.dto;

import clovar.howkiki.domain.order.entity.Order;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class OrderCanceledResponseDto {

    @JsonProperty("noticeName")
    private final String noticeName = "운영자의 주문 취소 알림";

    @JsonProperty("orderId")
    private final Long orderId;

    @JsonProperty("isTakeOut")
    private final Boolean isTakeOut;

    @JsonProperty("tableNumber")
    private final Long tableNumber;

    @JsonProperty("explanation")
    private final String explanation;


    public OrderCanceledResponseDto(Long orderId, Boolean isTakeOut, Long tableNumber, String explanation) {
        this.orderId = orderId;
        this.isTakeOut = isTakeOut;
        this.tableNumber = tableNumber;
        this.explanation = explanation;
    }

    // 객체 생성 방법 - 정적 팩토리 메서드 사용
    public static OrderCanceledResponseDto from(Order order, String explanation){
        return new OrderCanceledResponseDto(
                order.getOrderId(),
                order.getIsTakeOut(),
                order.getTableNumber(),
                explanation
        );
    }

}
