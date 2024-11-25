package clovar.howkiki.store.entity;

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
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 50)
    private String storeName;

    @NotNull
    private String address;

    @NotNull
    @Column(length = 500)
    private String operatingHours;

    @NotNull
    @Column(length = 20)
    private String phoneNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StoreCategory storeCategory; // ITALIAN, JAPANESE, etc.

}
