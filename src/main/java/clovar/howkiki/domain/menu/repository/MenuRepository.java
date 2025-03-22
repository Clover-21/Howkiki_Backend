package clovar.howkiki.domain.menu.repository;

import clovar.howkiki.domain.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    Optional<Menu> findMenuByMenuName(String menuName);

    // 해당 메뉴가 포함된 가게 조회
    @Query("SELECT m.store.storeId FROM Menu m WHERE m.menuId = :menuId")
    Long findStoreByMenuId(@Param("menuId") Long menuId);

    @Query("SELECT m FROM Menu m WHERE m.store.storeId = :storeId AND m.menuName = :menuName")
    Menu findMenuByStoreIdAndMenuName(@Param("storeId") Long storeId, @Param("menuName") String menuName);

    // 메뉴 이름 존재 여부 확인
    @Query("SELECT COUNT(m) > 0 FROM Menu m " +
            "WHERE m.store.storeId = :storeId AND m.menuName = :menuName ")
    boolean existsByMenuName(@Param("storeId")Long storeId, @Param("menuName")String menuName);

}
