package clovar.howkiki.menu.entity;

import clovar.howkiki.global.entity.BaseEntity;
import clovar.howkiki.order.entity.OrderDetail;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;

import java.util.List;

@Entity
@NoArgsConstructor
@SuperBuilder
@Getter
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "menuCategoryId", nullable = false)
    private MenuCategory menuCategory;

    @NotNull
    @Column(length = 100)
    private String name;

    @NotNull
    @Column(length = 10)
    private Long cost;

    @Column(length = 500)
    private String explanation;

    @Enumerated(EnumType.STRING)
    @NotNull
    private MenuStatus status; // AVAILABLE, OUT_OF_STOCK, NOT_AVAILABLE

    @NotNull
    private Boolean isSetMenu;

    @Column(length = 500)
    private String menuImgUrl;

    @OneToMany(mappedBy = "menu")
    private List<OrderDetail> orderDetails;

}
