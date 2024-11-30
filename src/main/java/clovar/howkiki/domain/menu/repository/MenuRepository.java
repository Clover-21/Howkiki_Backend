package clovar.howkiki.domain.menu.repository;

import clovar.howkiki.domain.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    Optional<Menu> findMenuByName(String menuName);
}
