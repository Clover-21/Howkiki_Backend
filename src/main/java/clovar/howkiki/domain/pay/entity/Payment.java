package clovar.howkiki.domain.pay.entity;

import clovar.howkiki.domain.order.entity.Order;
import clovar.howkiki.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.Id;


@Entity
@Getter
@NoArgsConstructor
@SuperBuilder
@Table(name = "Payments")
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long payId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)  //DB의 테이블 속성명으로 적기 (자바객체속성명X)
    private Order order;

    @Column(nullable = false)
    private String impUid;

    @Column(nullable = false)
    private Long orderPrice;

    @Column(nullable = false)
    private String payStatus;

}
