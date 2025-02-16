package clovar.howkiki.domain.suggestion.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
public class SuggestionRequestDto {

    private final String content;

    public SuggestionRequestDto(String content) {
        this.content = content;
    }

}
