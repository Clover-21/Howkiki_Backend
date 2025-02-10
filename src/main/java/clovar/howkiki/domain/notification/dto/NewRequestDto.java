package clovar.howkiki.domain.notification.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NewRequestDto {

    @JsonProperty("tableNumber")
    private Long tableNumber;

    @JsonProperty("content")
    private String content;

    public NewRequestDto(Long tableNumber, String content) {
        this.tableNumber = tableNumber;
        this.content = content;
    }
}
