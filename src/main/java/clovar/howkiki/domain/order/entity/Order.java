package clovar.howkiki.domain.order.entity;

import clovar.howkiki.domain.store.entity.Store;
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
@Table(name = "Orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "storeId", nullable = false)
    private Store store;

//    @NotNull
    @Column(length = 255)
    private String sessionToken;

    @NotNull
    private Boolean isTakeOut;

    @NotNull
    @Column(length = 10)
    private Long tableNumber;

    @NotNull
    @Column(length = 10)
    private Long orderPrice;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatus status; // NOT_YET_SENT, AWAITING_ACCEPTANCE, IN_PROGRESS, COMPLETED, PAID, USER_CANCELLED, ADMIN_CANCELLED

    @Enumerated(EnumType.STRING)
    private CancelReason cancelReason;

    private String soldOutMenu;

    private LocalDateTime expectedPrepTime;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;

}
