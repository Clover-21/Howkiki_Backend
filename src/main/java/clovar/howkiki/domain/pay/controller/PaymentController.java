package clovar.howkiki.domain.pay.controller;

import clovar.howkiki.domain.pay.dto.PaymentRequestDto;
import clovar.howkiki.domain.pay.dto.PaymentResponseDto;
import clovar.howkiki.domain.pay.service.PaymentService;
import clovar.howkiki.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    // 검증 코드 - 프론트에서 받은 impUid로 결제 금액 비교 검증
    @PostMapping("/verification")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<PaymentResponseDto> verifyPayment(@RequestBody PaymentRequestDto requestDto) {
        PaymentResponseDto responseDto = paymentService.verifyPayment(requestDto);
        return new ApiResponse<PaymentResponseDto>(
                HttpStatus.OK.value(),
                "결제 검증 성공",
                responseDto
        );
    }
}
