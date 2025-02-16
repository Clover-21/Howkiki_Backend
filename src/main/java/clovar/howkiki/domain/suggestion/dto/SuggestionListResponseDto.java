package clovar.howkiki.domain.suggestion.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter // JSON 직렬화를 위해 필요
@NoArgsConstructor(force = true)
public class SuggestionListResponseDto {
    private final Long storeId;
    private final List<SuggestionResponseDto> suggestionList;

    @Builder
    public SuggestionListResponseDto(Long storeId, List<SuggestionResponseDto> suggestionList) {
        this.storeId = storeId;
        this.suggestionList = suggestionList;
    }

}
