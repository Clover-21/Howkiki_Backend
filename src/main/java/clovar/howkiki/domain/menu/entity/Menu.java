package clovar.howkiki.domain.menu.entity;

import clovar.howkiki.domain.store.entity.Store;
import clovar.howkiki.global.entity.BaseEntity;
import clovar.howkiki.domain.order.entity.OrderDetail;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@NoArgsConstructor
@SuperBuilder
@Getter
@Table(name = "Menu")
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "storeId", nullable = false)
    private Store store;

    @NotNull
    @Column(length = 50)
    private String menuName;

    @NotNull
    @Column(length = 10)
    private Long cost;

    @Column(length = 50)
    private String menuCategory;

    @Enumerated(EnumType.STRING)
    @NotNull
    private MenuStatus menuStatus; // AVAILABLE, OUT_OF_STOCK, NOT_AVAILABLE

    @Column(length = 1024)
    private String menuImgUrl;

    @OneToMany(mappedBy = "menu")
    private List<OrderDetail> orderDetails;

}
