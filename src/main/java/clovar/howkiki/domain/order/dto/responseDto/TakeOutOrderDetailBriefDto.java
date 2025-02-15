package clovar.howkiki.domain.order.dto.responseDto;

import clovar.howkiki.domain.order.entity.OrderDetail;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TakeOutOrderDetailBriefDto {
    private String menuName;
    private Long quantity;
    private Long totalPrice;

    public TakeOutOrderDetailBriefDto(String menuName, Long quantity, Long totalPrice) {
        this.menuName = menuName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public static TakeOutOrderDetailBriefDto from(OrderDetail orderDetail) {
        return new TakeOutOrderDetailBriefDto(
                orderDetail.getMenu().getMenuName(),
                orderDetail.getQuantity(),
                orderDetail.getTotalPrice()
        );
    }
}