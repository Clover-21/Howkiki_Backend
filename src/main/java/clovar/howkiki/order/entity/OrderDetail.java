package clovar.howkiki.order.entity;

import clovar.howkiki.global.entity.BaseEntity;
import clovar.howkiki.menu.entity.Menu;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Entity
@NoArgsConstructor
@SuperBuilder
@Getter
public class OrderDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "orderId", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "menuId", nullable = false)
    private Menu menu;

    @NotNull
    @Column(length = 100)
    private Long quantity;

    @NotNull
    @Column(length = 10)
    private Long totalPrice;

}
