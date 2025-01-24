package clovar.howkiki.domain.order.dto.responseDto;

import clovar.howkiki.domain.order.entity.OrderDetail;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderDetailBriefDto {
    private String menuName;
    private Long quantity;

    public OrderDetailBriefDto(String menuName, Long quantity) {
        this.menuName = menuName;
        this.quantity = quantity;
    }

    // OrderDetail을 기반으로 OrderDetailBriefDto로 변환
    public static OrderDetailBriefDto from(OrderDetail orderDetail) {
        return new OrderDetailBriefDto(
                orderDetail.getMenu().getMenuName(),
                orderDetail.getQuantity()
        );
    }
}