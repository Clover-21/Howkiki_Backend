package clovar.howkiki.domain.notification.controller;

import clovar.howkiki.global.response.ApiResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/session-tokens")
@RequiredArgsConstructor
public class SessionTokenController {

    @GetMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<String> generateSessionToken(HttpSession session) {

        String sessionToken = UUID.randomUUID().toString();  // 랜덤한 세션 토큰 생성
        session.setAttribute("sessionToken", sessionToken);  // 세션에 저장

        return new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "세션 토큰 발급 성공",
                sessionToken
        );
    }
}
