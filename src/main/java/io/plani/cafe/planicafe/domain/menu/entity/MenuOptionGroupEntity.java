package io.plani.cafe.planicafe.domain.menu.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "menu_option_groups")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuOptionGroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "menu_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    foreignKeyDefinition = "FOREIGN KEY (menu_id) REFERENCES menus(id) ON DELETE CASCADE"
            )
    )
    @Comment("옵션그룹이 속한 메뉴")
    private MenuEntity menu;

    @Column(nullable = false)
    @Comment("옵션그룹의 이름")
    private String name;

    @Column(nullable = false)
    @Comment("옵션그룹의 필수여부")
    private Boolean isRequired;

    @OneToMany(mappedBy = "group",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true)
    private List<MenuOptionItemEntity> optionItems;

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
     * 옵션그룹에 필수여부를 변경
     *
     * @param isRequired 변경 필수여부
     */
    public void changeIsRequired(Boolean isRequired) {
        this.isRequired = isRequired;
    }

    /**
     * 옵션 그룹에 옵션 아이템을 추가
     *
     * @param item 옵션 아이템
     */
    public void addOptionItem(MenuOptionItemEntity item) {
        if (this.optionItems == null) {
            this.optionItems = new ArrayList<>();
        }
        this.optionItems.add(item);
    }

}
