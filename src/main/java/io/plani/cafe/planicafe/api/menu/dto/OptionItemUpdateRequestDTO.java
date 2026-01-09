package io.plani.cafe.planicafe.api.menu.dto;

import io.plani.cafe.planicafe.domain.menu.exception.MenuException;
import io.plani.cafe.planicafe.global.enums.ErrorCode;

public record OptionItemUpdateRequestDTO(
        Long id, // 옵션 아이템의 id
        String name, // 옵션 아이템의 이름
        int price, // 옵션 아이템의 가격
        int displayOrder // 옵션 아이템의 표시 순서
) {
    public OptionItemUpdateRequestDTO {
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
