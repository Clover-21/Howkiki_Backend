package clovar.howkiki.order.dto;

import lombok.*;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderRequestDto {

    private Long tableNumber;
    private List<OrderDetailDto> orderDetails;

}
