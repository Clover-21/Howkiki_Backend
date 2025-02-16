package clovar.howkiki.domain.notification.controller;

import clovar.howkiki.domain.notification.dto.NewRequestDto;
import clovar.howkiki.domain.notification.dto.NewRequestResponseDto;
import clovar.howkiki.domain.notification.service.NotificationService;
import clovar.howkiki.domain.notification.service.SseService;
import clovar.howkiki.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final SseService sseService;

    /* SSE 구독 - SSE 구독 요청 시 실행되는 API */
    @GetMapping(value = "/subscribe", produces =  MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@RequestHeader(name = "sessionToken") String sessionToken) {
        return sseService.subscribe(sessionToken);
    }

    /* 요청 사항 알림 */
    @PostMapping("/new-request")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<NewRequestResponseDto> NewRequestNotice(@RequestHeader(name = "sessionToken") String sessionToken,
                                                               @RequestBody NewRequestDto requestDto){
        NewRequestResponseDto responseDto = notificationService.sendNewRequestNotice(sessionToken, requestDto);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "사용자 요청 도착 알림 전송 성공",
                responseDto
        );
    }


}
