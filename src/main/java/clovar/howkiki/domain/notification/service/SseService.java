package clovar.howkiki.domain.notification.service;

import clovar.howkiki.global.exception.CustomException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import static clovar.howkiki.global.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SseService {

    private final ObjectMapper objectMapper; // json으로 변환 위함
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();  // 동시성 고려

    /* SSE 구독 - (클라이언트가 SSE 연결할 때 호출되어 알림 받을 준비) */
    public SseEmitter subscribe(String sessionToken) {
        // 기존 Emitter가 있다면 삭제
        if (emitters.containsKey(sessionToken)) {
            log.info("기존 SSE Emitter 삭제 - sessionToken: {}", sessionToken);
            emitters.remove(sessionToken);
        }

        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        emitters.put(sessionToken, sseEmitter);

        // 사용자에게 모든 데이터 전송되었다면 emitter 삭제
        sseEmitter.onCompletion(() -> {
            log.info("emitter 삭제: SSE Emitter 완료됨 - sessionToken: {}", sessionToken);
            emitters.remove(sessionToken);
        });
        // emitter의 유효시간 만료시 emmitter 삭제
        sseEmitter.onTimeout(() -> {
            log.info("emitter 삭제: SSE Emitter 타임아웃 - sessionToken: {}", sessionToken);
            emitters.remove(sessionToken);
        });

        // 503 에러 방지를 위해 초기 더미 데이터 전송
        try {
            sseEmitter.send(SseEmitter.event().name("connect").data("SSE 구독 성공!"));
        } catch (IOException e) {
            emitters.remove(sessionToken);
            log.error("❌ SSE - 더미 데이터 전송 실패");
            throw new CustomException(FAILED_TO_SEND_DUMMY_NOTICE, null);
        }

        // 일정 시간이 지나면 강제 삭제 - 자원 낭비 방지
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (emitters.containsKey(sessionToken)) {
                    log.info("SSE Emitter 강제 삭제 - sessionToken: {}", sessionToken);
                    emitters.remove(sessionToken);
                }
            }
        }, 24 * 60 * 60 * 1000); // 24시간 후 강제 삭제

        return sseEmitter;
    }


    /* 알림 전송 */
    public <T> void sendNotification(String sessionToken, T responseDto) {
        // 해당 세션 토큰을 가진 사용자의 SSE 연결을 찾음
        SseEmitter emitter = getSseEmitter(sessionToken);

        try {
            // JSON으로 변환
            String jsonData = objectMapper.writeValueAsString(responseDto);
            log.info("📢 알림 전송할 데이터(JSON 변환됨): {}", jsonData);

            // 알림 전송
            emitter.send(SseEmitter.event()
                    .name("notification")
                    .data(jsonData));
            log.info("✅ SSE 메시지 전송 완료!");

        } catch (Exception e) {
            emitters.remove(sessionToken);
            log.error("❌ SSE 메시지 전송 실패 - sessionToken: {} - {}", sessionToken, e.getMessage());
            throw new CustomException(FAILED_TO_SEND_NOTICE, null);
        }
    }


    // 세션토큰으로 emitter 조회
    private SseEmitter getSseEmitter(String sessionToken) {
        try {
            SseEmitter emitter = emitters.get(sessionToken);
            if (emitter == null) {
                log.warn("⚠️ SSE Emitter 없음 - sessionToken: {}", sessionToken);
                throw new CustomException(SSE_EMITTER_EMPTY, null);
            }
            return emitter;
        } catch (NullPointerException e){
            throw new CustomException(SSE_EMITTER_NOT_FOUND, null);
        } catch (Exception e){
            throw new CustomException(SESSION_TOKEN_NOT_VALID, null);
        }
    }

}