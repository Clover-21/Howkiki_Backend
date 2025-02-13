package clovar.howkiki.domain.notification.dto;

import clovar.howkiki.domain.order.entity.Order;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NewRequestResponseDto {

    @JsonProperty("noticeName")
    private String noticeName = "요청 사항 알림";

    @JsonProperty("orderId")
    private Long orderId;

    @JsonProperty("isTakeOut")
    private Boolean isTakeOut;

    @JsonProperty("tableNumber")
    private Long tableNumber;

    @JsonProperty("content")
    private String content;

    public NewRequestResponseDto(Long orderId, Boolean isTakeOut, Long tableNumber, String content) {
        this.orderId = orderId;
        this.isTakeOut = isTakeOut;
        this.tableNumber = tableNumber;
        this.content = content;
    }

    public static NewRequestResponseDto from(Order order, NewRequestDto requestDto){
        return new NewRequestResponseDto(
        order.getOrderId(),
        order.getIsTakeOut(),
        requestDto.getTableNumber(),
        requestDto.getContent()
        );
    }
}
