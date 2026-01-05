package io.plani.cafe.planicafe.domain.menu.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Table(name = "menu_option_groups")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuOptionGroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    @Comment("옵션그룹이 속한 메뉴")
    private MenuEntity menu;

    @Column(nullable = false)
    @Comment("옵션그룹의 이름")
    private String name;

    @Column(nullable = false)
    @Comment("옵션그룹의 필수여부")
    private Boolean isRequired;

    private static final String DEFAULT_NAME = "unnamed";

    @Builder
    public MenuOptionGroupEntity(MenuEntity menu,
                                 String name,
                                 Boolean isRequired) {
        this.menu = menu;
        this.name = name;
        this.isRequired = isRequired;
    }

    /**
     * 옵션그룹의 이름을 변경
     *
     * @param name 변경 이름
     */
    public void changeName(String name) {
        this.name = (name == null || name.isBlank()) ? DEFAULT_NAME : name;
    }

    /**
     * 옵션그룹의 필수여부를 변경
     *
     * @param isRequired 변경 필수여부
     */
    public void changeIsRequired(Boolean isRequired) {
        this.isRequired = isRequired;
    }
}
