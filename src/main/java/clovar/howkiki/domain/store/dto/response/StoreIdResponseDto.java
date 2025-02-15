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
    private String sessionToken;

    public StoreIdResponseDto(Long storeId, String storeName, String sessionToken) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.sessionToken = sessionToken;
    }

    public static StoreIdResponseDto from (Store store){
        return new StoreIdResponseDto(
                store.getStoreId(),
                store.getStoreName(),
                store.getSessionToken()
        );
    }

}
