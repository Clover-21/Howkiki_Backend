package clovar.howkiki.store.repository;

import clovar.howkiki.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StoreRepository extends JpaRepository<Store, Long> {
}
