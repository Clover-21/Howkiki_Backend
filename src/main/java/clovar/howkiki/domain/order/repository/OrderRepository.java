package clovar.howkiki.domain.order.repository;

import clovar.howkiki.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
