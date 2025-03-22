package clovar.howkiki.domain.menu.dto.responseDto;

import clovar.howkiki.domain.menu.entity.Menu;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
public class MenuCreateResponseDto {

    private final String storeName;
    private final Long menuId;
    private final String menuName;
    private final String menuImgUrl;

    public MenuCreateResponseDto(String storeName, Long menuId, String menuName, String menuImgUrl) {
        this.storeName = storeName;
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuImgUrl = menuImgUrl;
    }

    public static MenuCreateResponseDto from(Menu menu){
        return new MenuCreateResponseDto(
                menu.getStore().getStoreName(),
                menu.getMenuId(),
                menu.getMenuName(),
                menu.getMenuImgUrl()
        );
    }
}
