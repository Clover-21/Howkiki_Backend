package clovar.howkiki.domain.order.repository;

import clovar.howkiki.domain.order.entity.Order;
import clovar.howkiki.domain.order.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // 해당 가게의 모든 주문 조회
    @Query("SELECT o FROM Order o WHERE o.store.storeId = :storeId " +
            "AND o.status <> 'USER_CANCELLED' ")
    List<Order> findByStoreId(@Param("storeId") Long storeId);

    // 해당 가게의 포장 주문 조회 (AWAITING_ACCEPTANCE, IN_PROGRESS, COMPLETED 상태 인것만 + orderID의 역순 정렬
    @Query("SELECT o FROM Order o WHERE o.store.storeId = :storeId " +
            "AND o.isTakeOut = true " +
            "AND o.status IN ('AWAITING_ACCEPTANCE', 'IN_PROGRESS', 'COMPLETED')" +
            "ORDER BY o.orderId DESC")
    List<Order> findTakeOutOrderByStoreId(@Param("storeId") Long storeId);

    // 해당 가게의 테이블 주문 조회
    // 전송 전, 결제 완료된 주문 제외
    // 같은 테이블 번호의 주문이 여러개 있다면 가장 최근걸로 하나만 조회
    // 테이블 번호 오름차순으로 정렬
    @Query("SELECT o FROM Order o " + "WHERE o.store.storeId = :storeId " +
            "AND o.isTakeOut = false " +
            "AND o.status NOT IN ('NOT_YET_SENT', 'PAID') " +
            "AND o.orderId IN ( " +
            "  SELECT MAX(subO.orderId) FROM Order subO " +
            "  WHERE subO.store.storeId = :storeId " +
            "  AND subO.isTakeOut = false " +
            "  AND subO.status NOT IN ('NOT_YET_SENT', 'PAID') " +
            "  GROUP BY subO.tableNumber" +
            ") " +
            "ORDER BY o.tableNumber ASC")
    List<Order> findTableOrderByStoreId(@Param("storeId")Long storeId);

    // 해당 가게의 특정 상태의 주문 조회 (AWAITING_ACCEPTANCE, IN_PROGRESS, COMPLETED 상태 인것만 + orderID의 역순 정렬
    @Query("SELECT o FROM Order o WHERE o.store.storeId = :storeId " +
            "AND o.status = :status " +
            "ORDER BY o.orderId DESC")
    List<Order> findOrderByStoreIdAndStatus(@Param("storeId")Long storeId, @Param("status")OrderStatus status);


    // 해당 테이블의 주문 목록 조회 (상태가 AWAITING_ACCEPTANCE, IN_PROGRESS, COMPLETED 인 것 만)
    @Query("SELECT o From Order o WHERE o.store.storeId = :storeId " +
            "AND o.tableNumber = :tableNumber " +
            "AND o.status IN ('AWAITING_ACCEPTANCE', 'IN_PROGRESS', 'COMPLETED')" +
            "ORDER BY o.orderId DESC")
    List<Order> findOrderByTableNumber(@Param("storeId")Long storeId, @Param("tableNumber")Long tableNumber);

    Order findOrderByOrderId(@Param("orderId")Long orderId);

    // 해당 세션 토큰의 가장 최근 order 조회
    @Query(value = "SELECT * FROM `orders` o WHERE o.session_token = :userSessionToken ORDER BY o.order_id DESC LIMIT 1", nativeQuery = true)
    Order findRecentOrderBySessionToken(@Param("userSessionToken") String userSessionToken);

}
