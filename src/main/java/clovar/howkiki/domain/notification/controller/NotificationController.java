package clovar.howkiki.domain.notification.controller;

import clovar.howkiki.domain.notification.service.NotificationService;
import clovar.howkiki.domain.notification.service.SseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/stores/{storeId}/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final SseService sseService;

    /* SSE 구독 - SSE 구독 요청 시 실행되는 API */
    @GetMapping(value = "/subscribe", produces =  MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@RequestHeader(name = "sessionToken") String sessionToken) {
        return sseService.subscribe(sessionToken);
    }


}
