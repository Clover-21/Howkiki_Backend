package clovar.howkiki.domain.notification.service;

import clovar.howkiki.domain.notification.dto.NewOrderNoticeResponseDto;
import clovar.howkiki.domain.notification.dto.NewRequestDto;
import clovar.howkiki.domain.notification.dto.NewRequestResponseDto;
import clovar.howkiki.domain.notification.dto.OrderCanceledResponseDto;
import clovar.howkiki.domain.order.entity.Order;
import clovar.howkiki.domain.order.repository.OrderRepository;
import clovar.howkiki.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static clovar.howkiki.global.exception.ErrorCode.ORDER_NOT_FOUND;
import static clovar.howkiki.global.exception.ErrorCode.SESSION_TOKEN_EMPTY;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final SseService sseService;  // sse 관련 서비스 로직
    private final OrderRepository orderRepository;

    /* 새로운 주문 도착 알림 전송 */
    public void sendNewOrderNotice(Order order, String storeSessionToken) {

        checkSessionToken(storeSessionToken); // 토큰 유무 검증

        // 생성된 주문 객체를 바탕으로 알림 메시지 dto 생성
        NewOrderNoticeResponseDto responseDto = NewOrderNoticeResponseDto.from(order);

        // 알림 전송
        sseService.sendNotification(storeSessionToken, responseDto);

    }

    /* 운영자의 주문 취소 알림 */
    public void sendOrderCanceledByAdmin(Order order, String userSessionToken) {

        checkSessionToken(userSessionToken);

        String explanation = switch (order.getCancelReason()) {
            case OUT_OF_STOCK -> order.getSoldOutMenu() + " - 재료소진";
            case LAST_ORDER_ENDED -> "라스트 오더 종료";
            default -> "가게 기타 사정";
        };

        // 주문 취소 알림 dto 생성
        OrderCanceledResponseDto responseDto = OrderCanceledResponseDto.from(order, explanation);

        // 알림전송
        sseService.sendNotification(userSessionToken, responseDto);

    }

    /* 요청 사항 알림 */
    @Transactional(readOnly = true)  // 주문 조회를 위해 트랜잭션 필요
    public NewRequestResponseDto sendNewRequestNotice(String userSessionToken, NewRequestDto requestDto){

        checkSessionToken(userSessionToken);

        // 요청자의 가장 최근 order 조회
        Order userOrder = orderRepository.findRecentOrderBySessionToken(userSessionToken);
        // 검증
        if(userOrder == null){
            throw new CustomException(ORDER_NOT_FOUND, "/notification/new-request");
        }

        String storeSessionToken = userOrder.getStore().getSessionToken();

        // dto 생성
        NewRequestResponseDto responseDto = NewRequestResponseDto.from(userOrder, requestDto);

        // 알림 전송
        sseService.sendNotification(storeSessionToken, responseDto);
        return responseDto;
    }


    // 세션 토큰 유무 검증
    private void checkSessionToken(String sessionToken){
        if (sessionToken == null){
            throw new CustomException(SESSION_TOKEN_EMPTY, "null");
        }
    }

}
