package clovar.howkiki.domain.pay.dto;

import clovar.howkiki.domain.pay.entity.Payment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PaymentResponseDto {

    private Long payId;
    private Long orderId;
    private String payStatus;
    private LocalDateTime createdAt;

    public PaymentResponseDto(Long payId, Long orderId, String payStatus, LocalDateTime createdAt) {
        this.payId = payId;
        this.orderId = orderId;
        this.payStatus = payStatus;
        this.createdAt = createdAt;
    }

    // DTO 변환 메서드
    public static PaymentResponseDto from(Payment payment){
        return new PaymentResponseDto(
                payment.getPayId(),
                payment.getOrder().getOrderId(),
                payment.getPayStatus(),
                payment.getCreatedAt()

        );
    }
}
