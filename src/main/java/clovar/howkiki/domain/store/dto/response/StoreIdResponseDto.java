package clovar.howkiki.domain.store.dto.response;

import clovar.howkiki.domain.store.entity.Store;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
public class StoreIdResponseDto {
    private Long storeId;
    private String storeName;

    public StoreIdResponseDto(Long storeId, String storeName) {
        this.storeId = storeId;
        this.storeName = storeName;
    }

    public static StoreIdResponseDto from (Store store){
        return new StoreIdResponseDto(
                store.getStoreId(),
                store.getStoreName()
        );
    }

}
