package clovar.howkiki.domain.order.dto;

import clovar.howkiki.domain.order.entity.OrderDetail;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
public class OrderMenuListDetailDto {
    private Long menuId;
    private String menuCategory;
    private String menuName;
    private Long quantity;
    private Long totalPrice;

    public OrderMenuListDetailDto(Long menuId, String menuCategory, String menuName, Long quantity, Long totalPrice) {
        this.menuId = menuId;
        this.menuCategory = menuCategory;
        this.menuName = menuName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    // OrderDetail을 기반으로 OrderMenuListDetailDto 변환
    public static OrderMenuListDetailDto from(OrderDetail orderDetail) {
        return new OrderMenuListDetailDto(
                orderDetail.getMenu().getId(),           // 메뉴 ID
                orderDetail.getMenu().getMenuCategory().getMenuCategoryName(),     // 메뉴 카테고리
                orderDetail.getMenu().getName(),         // 메뉴 이름
                orderDetail.getQuantity(),               // 수량
                orderDetail.getTotalPrice()          // 총 가격
        );
    }
}