package io.plani.cafe.planicafe.api.menu.dto;

import io.plani.cafe.planicafe.domain.menu.entity.MenuEntity;
import io.plani.cafe.planicafe.domain.menu.vo.MenuStatus;
import lombok.Builder;

@Builder
public record MenuDTO(
        CategoryDTO category,
        String name,
        int price,
        MenuStatus status,
        int displayOrder
) {
    public static MenuDTO from(MenuEntity entity) {
        return MenuDTO.builder()
                .category(CategoryDTO.from(entity.getCategory()))
                .name(entity.getName())
                .price(entity.getPrice())
                .status(entity.getStatus())
                .displayOrder(entity.getDisplayOrder())
                .build();
    }
}
