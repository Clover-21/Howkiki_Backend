package clovar.howkiki.order.repository;

import clovar.howkiki.order.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
