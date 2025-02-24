package clovar.howkiki.domain.order.dto.responseDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(force = true)
public class AllTableOrderResponseDto {
    private final Long tableNumber;
    private final Long totalPrice;
    private final List<OrderDetailBriefDto> orderDetail;

    public AllTableOrderResponseDto(Long tableNumber, Long totalPrice, List<OrderDetailBriefDto> orderDetail) {
        this.tableNumber = tableNumber;
        this.totalPrice = totalPrice;
        this.orderDetail = orderDetail;
    }

    public static AllTableOrderResponseDto from(Long tableNumber, Long totalPrice, List<OrderDetailBriefDto> orderDetail){
        return new AllTableOrderResponseDto(
                tableNumber,
                totalPrice,
                orderDetail
        );
    }

}
