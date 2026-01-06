package io.plani.cafe.planicafe.api.menu.dto;

public record MenuCreateRequestDTO(
    Long categoryId, // 카테고리 id
    String name, // 메뉴 명
    int price, // 메뉴 가격
    int displayOrder // 메뉴 표시 순서
) {}
