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
    @Column(name = "store_id")
    private Long storeId;

    @NotNull
    @Column(name = "store_name", length = 50)
    private String storeName;

    @NotNull
    @Column(name = "sessionToken", length = 255)
    private String sessionToken;

    @NotNull
    @Column(name = "store_status")
    @Enumerated(value = EnumType.STRING)
    private StoreStatus storeStatus;

}
