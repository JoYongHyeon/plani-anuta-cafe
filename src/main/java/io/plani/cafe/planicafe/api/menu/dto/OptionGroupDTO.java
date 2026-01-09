package io.plani.cafe.planicafe.api.menu.dto;

import io.plani.cafe.planicafe.domain.menu.entity.MenuOptionGroupEntity;
import lombok.Builder;

@Builder
public record OptionGroupDTO(
        Long id, // 옵션 그룹의 id
        String name, //옵션 그룹 명
        Boolean isRequired, // 옵션 그룹의 필수 여부
        int displayOrder // 옵션 그룹의 표시 순서
) {
    public static OptionGroupDTO from(MenuOptionGroupEntity entity) {
        return OptionGroupDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .isRequired(entity.getIsRequired())
                .displayOrder(entity.getDisplayOrder())
                .build();
    }
}
