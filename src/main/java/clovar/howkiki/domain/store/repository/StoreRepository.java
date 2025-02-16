package clovar.howkiki.domain.store.repository;

import clovar.howkiki.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    // 해당 가게명의 가게id 조회
    Store findByStoreName(String storeName);

}
