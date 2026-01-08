package io.plani.cafe.planicafe.domain.menu.entity;

import io.plani.cafe.planicafe.domain.menu.exception.MenuException;
import io.plani.cafe.planicafe.global.entity.BaseEntity;
import io.plani.cafe.planicafe.global.enums.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Table(name = "menu_option_items",
       uniqueConstraints = @UniqueConstraint(
           name = "uk_option_group_display_order",
           columnNames = {"group_id", "display_order"}
       ))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuOptionItemEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    @Comment("옵션이 속한 그룹")
    private MenuOptionGroupEntity group;

    @Column(nullable = false)
    @Comment("옵션의 이름")
    private String name;

    @Column(nullable = false)
    @Comment("옵션의 금액")
    private int price;

    @Column(nullable = false)
    @Comment("옵션의 표시 순서")
    private int displayOrder;

    @Builder
    public MenuOptionItemEntity(MenuOptionGroupEntity group,
                                String name,
                                int price,
                                int displayOrder) {
        this.group = group;
        this.name = name;
        this.price = price;
        this.displayOrder = displayOrder;
    }

    /**
     * 옵션의 이름을 변경
     *
     * @param name 변경 이름
     */
    public void changeName(String name) {
        if (name == null || name.isBlank()) {
            throw new MenuException(ErrorCode.INVALID_INPUT_VALUE);
        }

        this.name = name;
    }

    /**
     * 옵션의 금액을 변경 (음수 가능)
     *
     * @param price 변경 금액
     */
    public void changePrice(int price) {
        if (price < 0) {
            throw new MenuException(ErrorCode.INVALID_INPUT_VALUE);
        }

        this.price = price;
    }

    /**
     * 옵션의 표시 순서를 변경
     *
     * @param order 변경 순서
     */
    public void changeDisplayOrder(int order) {
        if (order < 0) {
            throw new MenuException(ErrorCode.INVALID_INPUT_VALUE);
        }

        this.displayOrder = order;
    }

}
