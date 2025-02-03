package clovar.howkiki.domain.store.controller;

import clovar.howkiki.domain.store.dto.request.StoreIdRequestDto;
import clovar.howkiki.domain.store.dto.response.StoreIdResponseDto;
import clovar.howkiki.domain.store.service.StoreService;
import clovar.howkiki.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;

    /* 가게명으로 가게ID 조회 */
    @GetMapping
    public ApiResponse<StoreIdResponseDto> getStoreId(@RequestBody StoreIdRequestDto requestDto) {
        StoreIdResponseDto responseDto = storeService.getStoreId(requestDto);
        return new ApiResponse<StoreIdResponseDto>(
                HttpStatus.OK.value(),
                "가게 id 조회 성공",
                responseDto
        );
    }

}
