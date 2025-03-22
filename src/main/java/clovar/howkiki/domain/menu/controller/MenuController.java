package clovar.howkiki.domain.menu.controller;

import clovar.howkiki.domain.menu.dto.requestDto.MenuCreateRequestDto;
import clovar.howkiki.domain.menu.dto.responseDto.MenuCreateResponseDto;
import clovar.howkiki.domain.menu.dto.responseDto.MenuImgResponseDto;
import clovar.howkiki.domain.menu.service.MenuService;
import clovar.howkiki.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores/{storeId}/menu")
public class MenuController {

    private final MenuService menuService;

    /* 메뉴 등록 */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<MenuCreateResponseDto> createMenu(@PathVariable(name = "storeId") Long storeId,
                                                         @RequestPart(name = "menuImg") MultipartFile menuImg,
                                                         @RequestPart(name = "requestDto") MenuCreateRequestDto requestDto){
        MenuCreateResponseDto responseDto = menuService.createMenu(storeId, menuImg, requestDto);
        return new ApiResponse<MenuCreateResponseDto>(
                HttpStatus.CREATED.value(),
                "메뉴 사진 업로드 성공",
                responseDto
        );
    }


    /* 메뉴 사진 URL 조회 */
    @GetMapping("/img")
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
