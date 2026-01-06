package io.plani.cafe.planicafe.domain.menu.entity;

import io.plani.cafe.planicafe.domain.menu.vo.MenuStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Table(name = "menus") // 메뉴 카테고리 추가 시 displayOrder 제약조건 추가
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "category_id",
            foreignKey = @ForeignKey(
                    foreignKeyDefinition = "FOREIGN KEY (category_id) REFERENCES menu_categories(id) ON DELETE SET NULL"
            )
    )
    @Comment("메뉴가 속한 카테고리")
    private MenuCategory category;

    @Column(nullable = false)
    @Comment("메뉴의 이름")
    private String name;

    @Column(nullable = false)
    @Comment("메뉴의 금액")
    private int price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Comment("메뉴의 상태")
    private MenuStatus status;

    @Column(nullable = false)
    @Comment("메뉴의 표시 순서")
    private int displayOrder;

    private static final String DEFAULT_NAME = "unnamed";

    @Builder
    public MenuEntity(MenuCategory category,
                      String name,
                      int price,
                      int displayOrder) {
        this.category = category;
        this.name = name;
        this.price = price;
        this.displayOrder = displayOrder;

        // 시스템이 관리해야 할 초기값들
        this.status = MenuStatus.BLIND;
    }

    /**
     * 메뉴의 이름을 변경
     *
     * @param name 변경 이름
     */
    public void changeName(String name) {
        this.name = (name == null || name.isBlank()) ? DEFAULT_NAME : name;
    }

    /**
     * 메뉴의 금액을 변경
     *
     * @param price 변경 금액
     */
    public void changePrice(int price) {
        if (price < 0) throw new IllegalArgumentException("메뉴의 금액은 음수로 변경이 불가능합니다.");
        this.price = price;
    }

    /**
     * 메뉴의 표시 순서를 변경
     *
     * @param order 변경 순서
     */
    public void changeDisplayOrder(int order) {
        if (order < 0) throw new IllegalArgumentException("메뉴의 표시 순서는 음수로 변경이 불가능합니다.");
        this.displayOrder = order;
    }

}
