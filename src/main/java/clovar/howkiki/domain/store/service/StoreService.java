package clovar.howkiki.domain.store.service;

import clovar.howkiki.domain.store.dto.request.StoreIdRequestDto;
import clovar.howkiki.domain.store.dto.response.StoreIdResponseDto;
import clovar.howkiki.domain.store.entity.Store;
import clovar.howkiki.domain.store.repository.StoreRepository;
import clovar.howkiki.global.exception.CustomException;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static clovar.howkiki.global.exception.ErrorCode.MISSING_PARAMETER;
import static clovar.howkiki.global.exception.ErrorCode.STORE_NOT_FOUND;

@Service
@Getter
@Transactional
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    /* 가게명으로 가게ID 조회 */
    public StoreIdResponseDto getStoreId(StoreIdRequestDto requestDto){

        // 예외: 파라미터(가게명)이 누락된 경우
        if (requestDto.getStoreName() == null) {
            throw new CustomException(MISSING_PARAMETER, "/stores");
        }

        String storeName = requestDto.getStoreName();
        Store store = storeRepository.findByStoreName(storeName);

        // 예외 : 해당 이름의 가게가 없는 경우
        if (store == null){
            throw new CustomException(STORE_NOT_FOUND, "/stores");
        }

        return StoreIdResponseDto.from(store);
    }

}
