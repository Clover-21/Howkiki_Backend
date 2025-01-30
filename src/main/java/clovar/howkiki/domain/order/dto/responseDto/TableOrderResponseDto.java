package clovar.howkiki.domain.order.dto.responseDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class TableOrderResponseDto<T> {
    private Long tableNumber;
    private Long tableTotalPrice;
    private List<T> orderList;

    public TableOrderResponseDto(Long tableNumber, Long tableTotalPrice, List<T> orderList) {
        this.tableNumber = tableNumber;
        this.tableTotalPrice = tableTotalPrice;
        this.orderList = orderList;
    }

    public static <T> TableOrderResponseDto<T> from (Long tableNumber, Long price, List<T> orderList) {
        return new TableOrderResponseDto<T>(
                tableNumber,
                price,
                orderList
        );
    }
}
