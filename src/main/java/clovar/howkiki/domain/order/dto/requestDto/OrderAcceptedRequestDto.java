package clovar.howkiki.domain.order.dto.requestDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderAcceptedRequestDto {
    private Long expectedPrepMin;

    public OrderAcceptedRequestDto(Long expectedPrepMin) {
        this.expectedPrepMin = expectedPrepMin;
    }
}
