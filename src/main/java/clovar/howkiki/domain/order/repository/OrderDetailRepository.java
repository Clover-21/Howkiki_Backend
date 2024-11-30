package clovar.howkiki.domain.order.repository;

import clovar.howkiki.domain.order.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findOrderDetailByOrderId(Long orderId);
}
