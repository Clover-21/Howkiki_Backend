package clovar.howkiki.order.entity;

import clovar.howkiki.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@SuperBuilder
@Getter
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10)
    private Long tableNumber;

    @NotNull
    @Column(length = 10)
    private Long orderPrice;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatus status; // PENDING, PROCESSING, COMPLETED, CANCELED

    @Column(length = 50)
    private String cancelReason;

    private LocalDateTime expectedPrepTime;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;

}
