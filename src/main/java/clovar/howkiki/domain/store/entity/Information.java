package clovar.howkiki.domain.store.entity;

import clovar.howkiki.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@Getter
@SuperBuilder
public class Information extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "storeId", nullable = false)
    private Store store;

    private String wifi;
    private String restRoom;
    private String parking;
    private String reservation;
    private String paymentMethod;
    private String wifiQRImgUrl;

}
