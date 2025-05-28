package clovar.howkiki.domain.pay.service;

import clovar.howkiki.domain.order.entity.Order;
import clovar.howkiki.domain.order.entity.OrderStatus;
import clovar.howkiki.domain.order.repository.OrderRepository;
import clovar.howkiki.domain.order.service.OrderCreateService;
import clovar.howkiki.domain.order.service.OrderUpdateService;
import clovar.howkiki.domain.pay.dto.PaymentRequestDto;
import clovar.howkiki.domain.pay.dto.PaymentResponseDto;
import clovar.howkiki.domain.pay.entity.Payment;
import clovar.howkiki.domain.pay.repository.PaymentRepository;
import clovar.howkiki.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static clovar.howkiki.global.exception.ErrorCode.AMOUNT_NOT_EQUAL;
import static clovar.howkiki.global.exception.ErrorCode.NOT_PAID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PortOneService portOneService;
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final OrderUpdateService orderUpdateService;
    private final OrderCreateService orderCreateService;

    /* 결제 유효성 검증 */
    public PaymentResponseDto verifyPayment(PaymentRequestDto dto) {
        String methodUrl = "/payments/verification";

        // 1. PortOne 결제 정보 조회 - 라이브러리 이름이 겹쳐서 풀로 써줌
        com.siot.IamportRestClient.response.Payment paymentData = portOneService.getPaymentData(dto.getImpUid());

        // 2. 실제 결제 금액 및 상태
        Long paidAmount = paymentData.getAmount().longValue();
        String status = paymentData.getStatus();

        // 3. DB에서의 주문 금액
        Order order = orderRepository.findOrderByOrderId(dto.getOrderId());
        Long orderAmount = order.getOrderPrice();

        // 4. 결제 금액이 같은지 확인
        if (!paidAmount.equals(orderAmount)) {
            throw new CustomException(AMOUNT_NOT_EQUAL, methodUrl);
        }

        // 예외: 결제가 완료되지 않은 경우
        if (!"paid".equals(status)) {
            throw new CustomException(NOT_PAID, methodUrl);
        }

        // *결제가 완료된 경우 ORDER 주문 상태 변경
        if ("paid".equals(status)) {
            orderUpdateService.updateOrderStatus(order.getStore().getStoreId(), order.getOrderId(), OrderStatus.NOT_YET_SENT);

            // 스케줄러 호출 - 30초 후 상태 AWAITING_ACCEPTANCE로 변경
            orderCreateService.scheduleOrderStatusUpdate(order, methodUrl);

            // 스케줄러 호출 - 새로운 주문 도착 알림 발송
            orderCreateService.scheduleNewOrderNotice(order, methodUrl);
        }

        // 5. payment 객체 생성
        clovar.howkiki.domain.pay.entity.Payment payment = Payment.builder()
                .order(order)
                .impUid(dto.getImpUid())
                .orderPrice(paidAmount)
                .payStatus(status)
                .build();

        paymentRepository.save(payment);

        return PaymentResponseDto.from(payment);

    }
}
