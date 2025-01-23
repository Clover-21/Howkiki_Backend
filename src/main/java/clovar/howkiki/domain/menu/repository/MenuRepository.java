package clovar.howkiki.domain.menu.repository;

import clovar.howkiki.domain.menu.entity.Menu;
import clovar.howkiki.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    Optional<Menu> findMenuByMenuName(String menuName);

    // 해당 메뉴가 포함된 가게 조회
    @Query("SELECT m.store.storeId FROM Menu m WHERE m.menuId = :menuId")
    Long findStoreByMenuId(Long menuId);

}
