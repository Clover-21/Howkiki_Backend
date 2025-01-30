package clovar.howkiki.domain.order.dto.responseDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class TableOrderResponseDto {
    private Long tableNumber;
    private Long tableTotalPrice;
    private List<OrderDetailDto> orderList;

    public TableOrderResponseDto(Long tableNumber, Long tableTotalPrice, List<OrderDetailDto> orderList) {
        this.tableNumber = tableNumber;
        this.tableTotalPrice = tableTotalPrice;
        this.orderList = orderList;
    }

    public static TableOrderResponseDto from (Long tableNumber, Long price, List<OrderDetailDto> orderList) {
        return new TableOrderResponseDto(
                tableNumber,
                price,
                orderList
        );
    }
}
