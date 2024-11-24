package clovar.howkiki.order.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@SuperBuilder
@Getter
public class Order {

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
