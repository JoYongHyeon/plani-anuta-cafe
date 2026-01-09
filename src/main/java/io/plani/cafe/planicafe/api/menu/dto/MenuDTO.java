package io.plani.cafe.planicafe.api.menu.dto;

import io.plani.cafe.planicafe.domain.menu.entity.MenuEntity;
import io.plani.cafe.planicafe.domain.menu.vo.MenuStatus;
import lombok.Builder;

@Builder
public record MenuDTO(
        Long id, // 메뉴의 id
        CategoryDTO category, // 메뉴가 속한 카테고리
        String name, // 메뉴 명
        int price, // 메뉴의 가격
        MenuStatus status, // 메뉴의 상태
        int displayOrder // 메뉴의 표시 순서
) {
    public static MenuDTO from(MenuEntity entity) {
        return MenuDTO.builder()
                .id(entity.getId())
                .category(CategoryDTO.from(entity.getCategory()))
                .name(entity.getName())
                .price(entity.getPrice())
                .status(entity.getStatus())
                .displayOrder(entity.getDisplayOrder())
                .build();
    }
}
