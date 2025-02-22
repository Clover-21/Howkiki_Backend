package clovar.howkiki.domain.order.dto.responseDto;

import clovar.howkiki.domain.order.entity.OrderDetail;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderDetailBriefWithPriceDto {
    private String menuName;
    private Long quantity;
    private Long totalPrice;

    public OrderDetailBriefWithPriceDto(String menuName, Long quantity, Long totalPrice) {
        this.menuName = menuName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public static OrderDetailBriefWithPriceDto from(OrderDetail orderDetail) {
        return new OrderDetailBriefWithPriceDto(
                orderDetail.getMenu().getMenuName(),
                orderDetail.getQuantity(),
                orderDetail.getTotalPrice()
        );
    }
}