package io.plani.cafe.planicafe.api.menu.dto;

import io.plani.cafe.planicafe.domain.menu.exception.MenuException;
import io.plani.cafe.planicafe.global.enums.ErrorCode;

public record OptionGroupCreateRequestDTO(
    Long menuId, // 옵션그룹이 속한 메뉴 id
    String name, // 옵션그룹의 이름
    Boolean isRequired, // 옵션그룹의 필수여부
    int displayOrder // 메뉴 표시 순서
) {
    public OptionGroupCreateRequestDTO {
        if (name == null || name.isBlank()) {
            throw new MenuException(ErrorCode.INVALID_INPUT_VALUE);
        }

        if (displayOrder < 0) {
            throw new MenuException(ErrorCode.INVALID_INPUT_VALUE);
        }
    }
}
