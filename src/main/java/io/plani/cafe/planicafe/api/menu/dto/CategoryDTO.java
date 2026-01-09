package io.plani.cafe.planicafe.api.menu.dto;

import io.plani.cafe.planicafe.domain.menu.entity.MenuCategoryEntity;
import lombok.Builder;

@Builder
public record CategoryDTO(
        Long id, // 카테고리의 id
        String name, // 카테고리 명
        int displayOrder // 카테고리의 표시순서
) {
    public static CategoryDTO from(MenuCategoryEntity entity) {
        return CategoryDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .displayOrder(entity.getDisplayOrder())
                .build();
    }
}
