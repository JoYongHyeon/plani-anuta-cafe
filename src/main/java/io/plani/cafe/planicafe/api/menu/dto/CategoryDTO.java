package io.plani.cafe.planicafe.api.menu.dto;

import io.plani.cafe.planicafe.domain.menu.entity.MenuCategoryEntity;
import lombok.Builder;

@Builder
public record CategoryDTO(
        String name,
        int displayOrder
) {
    public static CategoryDTO from(MenuCategoryEntity entity) {
        return CategoryDTO.builder()
                .name(entity.getName())
                .displayOrder(entity.getDisplayOrder())
                .build();
    }
}
