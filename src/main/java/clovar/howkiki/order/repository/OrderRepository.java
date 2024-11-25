package clovar.howkiki.order.repository;

import clovar.howkiki.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.storeId = :storeId")
    List<Order> findOrderByStoreId(@Param("storeId") Long storeId);
}
