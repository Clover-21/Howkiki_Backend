package clovar.howkiki.domain.order.dto.requestDto;

import clovar.howkiki.domain.order.entity.CancelReason;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderCancelRequestDto {
    private CancelReason cancelReason;
    private String soldOutMenu;

    public OrderCancelRequestDto(CancelReason cancelReason, String soldOutMenu) {
        this.cancelReason = cancelReason;
        this.soldOutMenu = soldOutMenu;
    }
}
