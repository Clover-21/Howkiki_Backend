package clovar.howkiki.domain.menu.dto.responseDto;

import clovar.howkiki.domain.menu.entity.Menu;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
public class MenuImgResponseDto {

    private final Long menuId;
    private final String menuName;
    private final String menuImgUrl;

    public MenuImgResponseDto(Long menuId, String menuName, String menuImgUrl) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuImgUrl = menuImgUrl;
    }

    public static MenuImgResponseDto from(Menu menu){
        return new MenuImgResponseDto(
                menu.getMenuId(),
                menu.getMenuName(),
                menu.getMenuImgUrl()
        );
    }
}
