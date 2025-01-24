package clovar.howkiki.domain.order.repository;

import clovar.howkiki.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // 해당 가게의 모든 주문 조회
    @Query("SELECT o FROM Order o WHERE o.store.storeId = :storeId ")
    List<Order> findByStoreId(Long storeId);


    // 해당 가게의 포장 주문 조회 (AWAITING_ACCEPTANCE, IN_PROGRESS, COMPLETED 상태 인것만 + orderID의 역순 정렬
    @Query("SELECT o FROM Order o WHERE o.store.storeId = :storeId AND o.isTakeOut = true " +
            "AND o.status IN ('AWAITING_ACCEPTANCE', 'IN_PROGRESS', 'COMPLETED')" +
            "ORDER BY o.orderId DESC")
    List<Order> findTakeOutOrderByStoreId(Long storeId);
}
