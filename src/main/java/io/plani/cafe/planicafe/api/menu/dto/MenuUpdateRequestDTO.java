package io.plani.cafe.planicafe.api.menu.dto;

import io.plani.cafe.planicafe.domain.menu.exception.MenuException;
import io.plani.cafe.planicafe.domain.menu.vo.MenuStatus;
import io.plani.cafe.planicafe.global.enums.ErrorCode;

public record MenuUpdateRequestDTO(
    Long id, // 메뉴 id
    Long categoryId, // 카테고리 id
    String name, // 메뉴 명
    int price, // 메뉴 가격
    MenuStatus status,
    int displayOrder // 메뉴 표시 순서
) {
    public MenuUpdateRequestDTO {
        if (name == null || name.isBlank()) {
            throw new MenuException(ErrorCode.INVALID_INPUT_VALUE);
        }

        if (price < 0) {
            throw new MenuException(ErrorCode.INVALID_INPUT_VALUE);
        }

        if (displayOrder < 0) {
            throw new MenuException(ErrorCode.INVALID_INPUT_VALUE);
        }
    }
}
