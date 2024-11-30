package clovar.howkiki.domain.order.dto;

import clovar.howkiki.domain.order.entity.OrderDetail;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
public class OrderDetailDto {

    @NotNull(message = "메뉴 이름은 필수입니다.")
    private String menuName;

    @NotNull(message = "수량은 필수입니다.")
    @Min(value = 1, message = "수량은 1 이상이어야 합니다.")
    private Long quantity;

    public OrderDetailDto(String menuName, Long quantity) {
        this.menuName = menuName;
        this.quantity = quantity;
    }

    public static OrderDetailDto from (OrderDetail orderDetail){
        return new OrderDetailDto(
                orderDetail.getMenu().getName(),
                orderDetail.getQuantity()
        );
    }
}