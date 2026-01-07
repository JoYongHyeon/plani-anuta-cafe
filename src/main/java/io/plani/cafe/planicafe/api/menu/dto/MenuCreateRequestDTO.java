package io.plani.cafe.planicafe.api.menu.dto;

public record MenuCreateRequestDTO(
    Long categoryId, // 카테고리 id
    String name, // 메뉴 명
    int price, // 메뉴 가격
    int displayOrder // 메뉴 표시 순서
) {
    public MenuCreateRequestDTO {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("메뉴의 이름은 필수 값 입니다.");
        }

        if (price < 0) {
            throw new IllegalArgumentException("메뉴의 가격은 음수로 설정이 불가능합니다.");
        }

        if (displayOrder < 0) {
            throw new IllegalArgumentException("메뉴의 표시 순서는 음수로 설정이 불가능합니다.");
        }
    }
}
