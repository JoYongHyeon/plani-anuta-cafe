package io.plani.cafe.planicafe.api.menu.dto;

public record CategoryCreateRequestDTO(
    String name, // 카테고리 명
    int displayOrder // 표시 순서
) {}
