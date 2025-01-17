package clovar.howkiki.domain.order.entity;

import clovar.howkiki.global.entity.BaseEntity;
import clovar.howkiki.domain.menu.entity.Menu;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@SuperBuilder
@Getter
@Table(name = "OrderDetails")
public class OrderDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderDetailId;

    @ManyToOne
    @JoinColumn(name = "orderId", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "menuId", nullable = false)
    private Menu menu;

    @NotNull
    @Column(length = 10)
    private Long quantity;

    @NotNull
    @Column(length = 10)
    private Long totalPrice;

}
