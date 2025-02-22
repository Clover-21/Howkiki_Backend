package clovar.howkiki.domain.order.dto.responseDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(force = true)
public class AllTableOrderResponseDto {
    private final Long tableNumber;
    private final List<OrderDetailBriefDto> orderDetail;

    public AllTableOrderResponseDto(Long tableNumber, List<OrderDetailBriefDto> orderDetail) {
        this.tableNumber = tableNumber;
        this.orderDetail = orderDetail;
    }

    public static AllTableOrderResponseDto from(Long tableNumber, List<OrderDetailBriefDto> orderDetail){
        return new AllTableOrderResponseDto(
                tableNumber,
                orderDetail
        );
    }

}
