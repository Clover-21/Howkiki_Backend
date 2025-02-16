package clovar.howkiki.domain.suggestion.controller;

import clovar.howkiki.domain.suggestion.dto.SuggestionDetailResponseDto;
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

}
