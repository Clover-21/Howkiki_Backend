package clovar.howkiki.domain.order.dto.requestDto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderCreateRequestDto {

    private Boolean isTakeOut;
    private Long tableNumber;
    private List<FinalOrderDetailDto> finalOrderDetails;

}
