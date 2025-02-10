package clovar.howkiki.domain.notification.dto;

import clovar.howkiki.domain.order.entity.Order;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class NewOrderNoticeResponseDto {

    @JsonProperty("noticeName")
    private final String noticeName = "새로운 주문 도착 알림";

    @JsonProperty("orderId")
    private final Long orderId;

    @JsonProperty("isTakeOut")
    private final Boolean isTakeOut;

    @JsonProperty("tableNumber")
    private final Long tableNumber;

    @JsonProperty("createdAt")
    private final String createdAt;

    public NewOrderNoticeResponseDto(Long orderId, Boolean isTakeOut, Long tableNumber, String createdAt) {
        this.orderId = orderId;
        this.isTakeOut = isTakeOut;
        this.tableNumber = tableNumber;
        this.createdAt = createdAt;
    }

    // 객체 생성 방법 - 정적 팩토리 메서드 사용
    public static NewOrderNoticeResponseDto from(Order order){
        return new NewOrderNoticeResponseDto(
                order.getOrderId(),
                order.getIsTakeOut(),
                order.getTableNumber(),
                order.getCreatedAt().toString()
        );
    }

}
