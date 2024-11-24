package clovar.howkiki.menu.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;


@Entity
@NoArgsConstructor
@SuperBuilder
@Getter
public class SetMenuDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "setMenuId", nullable = false)
    private Menu setMenu;

    @ManyToOne
    @JoinColumn(name = "includedMenuId", nullable = false)
    private Menu includedMenu;

}
