package clovar.howkiki.domain.menu.controller;

import clovar.howkiki.domain.menu.dto.MenuImgResponseDto;
import clovar.howkiki.domain.menu.service.MenuService;
import clovar.howkiki.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores/{storeId}")
public class MenuController {

    private final MenuService menuService;

    /* 메뉴 사진 URL 조회 */
    @GetMapping("/menu/img")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<MenuImgResponseDto> getMenuImgUrl(@PathVariable(name = "storeId") Long storeId,
                                                         @RequestParam(name = "menuName") String menuName) {
        MenuImgResponseDto responseDto = menuService.getMenuImgUrl(storeId, menuName);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "메뉴 사진 URL 조회 성공",
                responseDto
        );
    }


}
