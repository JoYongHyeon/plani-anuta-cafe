package io.plani.cafe.planicafe.api.menu.dto;

import io.plani.cafe.planicafe.domain.menu.entity.MenuOptionItemEntity;
import lombok.Builder;

@Builder
public record OptionItemDTO(
        Long id, // 옵션 아이템의 id
        String name, //옵션 아이템 명
        int price, // 옵션 아이템의 가격
        int displayOrder // 옵션 아이템의 표시순서
) {
    public static OptionItemDTO from(MenuOptionItemEntity entity) {
        return OptionItemDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .price(entity.getPrice())
                .displayOrder(entity.getDisplayOrder())
                .build();
    }
}
