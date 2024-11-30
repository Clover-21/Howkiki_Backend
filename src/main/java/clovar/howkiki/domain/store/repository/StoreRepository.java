package clovar.howkiki.domain.store.repository;

import clovar.howkiki.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StoreRepository extends JpaRepository<Store, Long> {
}
