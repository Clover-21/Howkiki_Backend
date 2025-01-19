package clovar.howkiki.domain.store.entity;

import clovar.howkiki.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Entity
@NoArgsConstructor
@SuperBuilder
@Getter
@Table(name = "Stores")
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeId;

    @NotNull
    @Column(length = 50)
    private String storeName;

    @NotNull
    private StoreStatus storeStatus;



}
