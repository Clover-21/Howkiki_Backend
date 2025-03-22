package clovar.howkiki.domain.menu.dto.requestDto;

import clovar.howkiki.domain.menu.entity.MenuStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenuCreateRequestDto {

    private String menuName;
    private Long cost;
    private String menuCategory;
    private MenuStatus menuStatus;

    public MenuCreateRequestDto(String menuName, Long cost, String menuCategory, MenuStatus menuStatus) {
        this.menuName = menuName;
        this.cost = cost;
        this.menuCategory = menuCategory;
        this.menuStatus = menuStatus;
    }

    public MenuCreateRequestDto(String menuName, Long cost, MenuStatus menuStatus) {
        this.menuName = menuName;
        this.cost = cost;
        this.menuStatus = menuStatus;
    }

}
