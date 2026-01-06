package io.plani.cafe.planicafe.domain.menu.service;

/**
 * 메뉴 서비스 인터페이스
 */
public interface MenuService {

    /**
     * 메뉴 생성
     *
     * @param categoryId 카테고리 id
     * @param menuName 메뉴 명
     * @param price 메뉴 가격
     * @param displayOrder 메뉴 표시순서
     */
    void createMenu(Long categoryId, String menuName, int price, int displayOrder);

    /**
     * 메뉴의 카테고리 생성
     *
     * @param categoryName 카테고리 명
     * @param displayOrder 카테고리 표시순서
     */
    void createCategory(String categoryName, int displayOrder);
}