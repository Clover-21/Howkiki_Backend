package clovar.howkiki.domain.pay.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentRequestDto {
    private String impUid;       // 아임포트 고유 결제 ID
    private Long merchantUid;  // 포트원 고객사 ID
    private Long orderId;
}
