package clovar.howkiki.domain.suggestion.controller;

import clovar.howkiki.domain.suggestion.dto.SuggestionDetailResponseDto;
import clovar.howkiki.domain.suggestion.dto.SuggestionListResponseDto;
import clovar.howkiki.domain.suggestion.dto.SuggestionRequestDto;
import clovar.howkiki.domain.suggestion.dto.SuggestionResponseDto;
import clovar.howkiki.domain.suggestion.service.SuggestionService;
import clovar.howkiki.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stores/{storeId}/suggestions")
@RequiredArgsConstructor
public class SuggestionController {

    private final SuggestionService suggestionService;

    /* 건의사항 등록 */
    @PostMapping()
    public ApiResponse<SuggestionDetailResponseDto> createSuggestion(@PathVariable(name = "storeId") Long storeId,
                                                                     @RequestBody SuggestionRequestDto requestDto){
        SuggestionDetailResponseDto responseDto = suggestionService.createSuggestion(storeId, requestDto);
        return new ApiResponse<SuggestionDetailResponseDto>(
                HttpStatus.CREATED.value(),
                "건의 사항 등록 성공",
                responseDto
        );
    }

    /* 건의사항 목록 조회 */
    @GetMapping("/all")
    public ApiResponse<SuggestionListResponseDto> getAllSuggestions(@PathVariable(name = "storeId") Long storeId){
        SuggestionListResponseDto responseDto = suggestionService.getAllSuggestions(storeId);
        return new ApiResponse<SuggestionListResponseDto>(
                HttpStatus.OK.value(),
                "건의사항 전체 조회 성공",
                responseDto
        );
    }

}
