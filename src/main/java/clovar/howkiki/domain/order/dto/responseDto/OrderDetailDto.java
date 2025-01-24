package clovar.howkiki.domain.order.dto.responseDto;

import clovar.howkiki.domain.order.entity.OrderDetail;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderDetailDto {
    private Long menuId;
    private String menuCategory;
    private String menuName;
    private Long quantity;
    private Long totalPrice;

    public OrderDetailDto(Long menuId, String menuCategory, String menuName, Long quantity, Long totalPrice) {
        this.menuId = menuId;
        this.menuCategory = menuCategory;
        this.menuName = menuName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    // OrderDetail을 기반으로 OrderDetailDto 변환
    public static OrderDetailDto from(OrderDetail orderDetail) {
        return new OrderDetailDto(
                orderDetail.getMenu().getMenuId(),           // 메뉴 ID
                orderDetail.getMenu().getMenuCategory(),     // 메뉴 카테고리
                orderDetail.getMenu().getMenuName(),         // 메뉴 이름
                orderDetail.getQuantity(),               // 수량
                orderDetail.getTotalPrice()          // 총 가격
        );
    }
}