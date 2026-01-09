package io.plani.cafe.planicafe.api.menu.dto;

import io.plani.cafe.planicafe.domain.menu.exception.MenuException;
import io.plani.cafe.planicafe.global.enums.ErrorCode;

public record OptionItemCreateRequestDTO(
    Long groupId, // 옵션아이템이 속한 그룹 id
    String name, // 옵션아이템의 이름
    int price, // 옵션아이템의 가격
    int displayOrder // 메뉴 표시 순서
) {
    public OptionItemCreateRequestDTO {
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
