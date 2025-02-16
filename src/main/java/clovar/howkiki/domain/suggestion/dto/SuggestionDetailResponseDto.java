package clovar.howkiki.domain.suggestion.dto;

import clovar.howkiki.domain.suggestion.entity.Suggestion;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(force = true)
public class SuggestionDetailResponseDto {
    private final Long suggestionId;
    private final Long storeId;
    private final String content;
    private final LocalDateTime createdAt;

    @Builder
    public SuggestionDetailResponseDto(Long suggestionId, Long storeId, String content, LocalDateTime createdAt) {
        this.suggestionId = suggestionId;
        this.storeId = storeId;
        this.content = content;
        this.createdAt = createdAt;
    }

    public static SuggestionDetailResponseDto from (Suggestion suggestion){
        return SuggestionDetailResponseDto.builder()
                .suggestionId(suggestion.getSuggestionId())
                .storeId(suggestion.getStore().getStoreId())
                .content(suggestion.getContent())
                .createdAt(suggestion.getCreatedAt())
                .build();
    }
}
