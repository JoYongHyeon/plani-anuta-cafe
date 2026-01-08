package io.plani.cafe.planicafe.api.menu.dto;

public record CategoryCreateRequestDTO(
    String name, // 카테고리 명
    int displayOrder // 표시 순서
) {
    public CategoryCreateRequestDTO {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("카테고리의 이름은 필수 값 입니다.");
        }

        if (displayOrder < 0) {
            throw new IllegalArgumentException("카테고리의 표시 순서는 음수로 설정이 불가능합니다.");
        }
    }
}
