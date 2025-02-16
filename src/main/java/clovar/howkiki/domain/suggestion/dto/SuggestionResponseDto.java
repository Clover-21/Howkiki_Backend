package clovar.howkiki.domain.suggestion.dto;

import clovar.howkiki.domain.suggestion.entity.Suggestion;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(force = true)
public class SuggestionResponseDto {
    private final Long suggestionId;
    private final String content;
    private final LocalDateTime createdAt;

    @Builder
    public SuggestionResponseDto(Long suggestionId, String content, LocalDateTime createdAt) {
        this.suggestionId = suggestionId;
        this.content = content;
        this.createdAt = createdAt;
    }

    public static SuggestionResponseDto from (Suggestion suggestion){
        return SuggestionResponseDto.builder()
                .suggestionId(suggestion.getSuggestionId())
                .content(suggestion.getContent())
                .createdAt(suggestion.getCreatedAt())
                .build();
    }
}
