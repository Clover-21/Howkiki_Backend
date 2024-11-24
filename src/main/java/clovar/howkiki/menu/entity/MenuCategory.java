package clovar.howkiki.menu.entity;

import clovar.howkiki.global.entity.BaseEntity;
import clovar.howkiki.store.entity.Store;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;

@Entity
@NoArgsConstructor
@SuperBuilder
@Getter
public class MenuCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "storeId", nullable = false)
    private Store store;

    @NotNull
    @Column(length = 100)
    private String menuCategoryName;

    @Nullable
    @Column(length = 500)
    private String explanation;


}
