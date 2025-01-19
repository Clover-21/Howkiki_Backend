package clovar.howkiki.domain.suggestion.entity;

import clovar.howkiki.domain.store.entity.Store;
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
@Table(name = "Suggestions")
public class Suggestion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long suggestionId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "storeId", nullable = false)
    private Store store;

    @NotNull
    private String content;

}
