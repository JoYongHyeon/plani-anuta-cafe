package io.plani.cafe.planicafe.api.menu.dto;

import io.plani.cafe.planicafe.domain.menu.exception.MenuException;
import io.plani.cafe.planicafe.global.enums.ErrorCode;

public record CategoryCreateRequestDTO(
    String name, // 카테고리 명
    int displayOrder // 표시 순서
) {
    public CategoryCreateRequestDTO {
        if (name == null || name.isBlank()) {
            throw new MenuException(ErrorCode.INVALID_INPUT_VALUE);
        }

        if (displayOrder < 0) {
            throw new MenuException(ErrorCode.INVALID_INPUT_VALUE);
        }
    }
}
