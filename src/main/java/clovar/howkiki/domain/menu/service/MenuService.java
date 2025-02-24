package clovar.howkiki.domain.menu.service;

import clovar.howkiki.domain.menu.dto.MenuImgResponseDto;
import clovar.howkiki.domain.menu.entity.Menu;
import clovar.howkiki.domain.menu.repository.MenuRepository;
import clovar.howkiki.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static clovar.howkiki.global.exception.ErrorCode.MENU_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {

    private final MenuRepository menuRepository;

    /* 메뉴 사진 URL 조회 */
    public MenuImgResponseDto getMenuImgUrl(Long storeId, String menuName){

        // 메뉴 찾기
        Menu menu = menuRepository.findMenuByStoreIdAndMenuName(storeId, menuName);

        // 해당 이름의 메뉴가 존재하지 않는 경우
        String methodUrl = "/stores/"+ storeId + "/menu/img";
        if(menu == null){
            throw new CustomException(MENU_NOT_FOUND, methodUrl);
        }

        return MenuImgResponseDto.from(menu);
    }
}
