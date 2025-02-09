package clovar.howkiki.domain.notification.service;

import clovar.howkiki.domain.notification.dto.NewOrderNoticeResponseDto;
import clovar.howkiki.domain.order.entity.Order;
import clovar.howkiki.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static clovar.howkiki.global.exception.ErrorCode.SESSION_TOKEN_EMPTY;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class NotificationService {

    private final SseService sseService;  //sse 관련 서비스 로직

    /* 새로운 주문 도착 알림 전송 */
    public void sendNewOrderNotice(Order order, String storeSessionToken) {

        checkSessionToken(storeSessionToken); // 토큰 유무 검증

        // 생성된 주문 객체를 바탕으로 알림 메시지 dto 생성
        NewOrderNoticeResponseDto responseDto = NewOrderNoticeResponseDto.from(order);

        // 알림 전송
        sseService.sendNotification(storeSessionToken, responseDto);

    }

    // 세션 토큰 유무 검증
    private void checkSessionToken(String sessionToken){
        if (sessionToken == null){
            throw new CustomException(SESSION_TOKEN_EMPTY, "null");
        }
    }
}
