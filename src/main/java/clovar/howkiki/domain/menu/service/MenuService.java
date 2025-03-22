package clovar.howkiki.domain.menu.service;

import clovar.howkiki.domain.menu.dto.requestDto.MenuCreateRequestDto;
import clovar.howkiki.domain.menu.dto.responseDto.MenuCreateResponseDto;
import clovar.howkiki.domain.menu.dto.responseDto.MenuImgResponseDto;
import clovar.howkiki.domain.menu.entity.Menu;
import clovar.howkiki.domain.menu.repository.MenuRepository;
import clovar.howkiki.domain.store.entity.Store;
import clovar.howkiki.domain.store.repository.StoreRepository;
import clovar.howkiki.global.exception.CustomException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import static clovar.howkiki.global.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {

    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    // AWS S3 Client
    private final AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucket}")   // 버킷 이름 가져오기
    private String bucket;

    @Transactional
    /* 메뉴 등록 */
    public MenuCreateResponseDto createMenu(Long storeId, MultipartFile menuImg, MenuCreateRequestDto requestDto) {

        String methodUrl = "/stores/"+ storeId + "/menu";

        // 메뉴 이름 중복 체크
        if (menuRepository.existsByMenuName(storeId, requestDto.getMenuName())){
            throw new CustomException(MENU_NAME_ALREADY_EXISTS, methodUrl);
        }
        // 해당 가게 찾기
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(STORE_ID_NOT_FOUND, methodUrl));

        // 이미지 저장 및 업로드
        String savedImgUrl = uploadImg(menuImg);

        // 메뉴 객체 생성
        Menu newMenu = Menu.builder()
                .store(store)
                .menuName(requestDto.getMenuName())
                .cost(requestDto.getCost())
                .menuCategory(requestDto.getMenuCategory())
                .menuStatus(requestDto.getMenuStatus())
                .menuImgUrl(savedImgUrl)
                .build();

        // 저장
        Menu savedMenu = menuRepository.save(newMenu);
        return MenuCreateResponseDto.from(savedMenu);
    }

    // 이미지 업로드 메서드
    private String uploadImg(MultipartFile menuImg){

        if (menuImg == null || menuImg.isEmpty()) {
            return null;
        }

        String fileName =  "images/" + UUID.randomUUID() + "_" + menuImg.getOriginalFilename();

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(menuImg.getSize());
        objectMetadata.setContentType(menuImg.getContentType());

        try(InputStream inputStream = menuImg.getInputStream()){
            s3Client.putObject(bucket, fileName, inputStream, objectMetadata);
        } catch (IOException e){
            throw new CustomException(FAILED_TO_UPLOAD_IMG, null);
        }
        // 실제 저장된 이미지 url 주소 반환
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, s3Client.getRegionName(), fileName);
    }


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
