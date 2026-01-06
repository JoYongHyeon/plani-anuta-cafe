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
@Table(name = "menu_categories")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Comment("카테고리의 이름")
    private String name;

    @Column(nullable = false)
    @Comment("카테고리의 표시 순서")
    private int displayOrder;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<MenuEntity> menus;

    @Builder
    public MenuCategory(String name, int displayOrder) {
        this.name = name;
        this.displayOrder = displayOrder;
    }
}
