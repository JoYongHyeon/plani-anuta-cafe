package io.plani.cafe.planicafe.domain.menu.service;

import io.plani.cafe.planicafe.api.menu.dto.CategoryCreateRequestDTO;
import io.plani.cafe.planicafe.api.menu.dto.MenuCreateRequestDTO;

/**
 * 메뉴 서비스 인터페이스
 */
public interface MenuService {

    /**
     * 메뉴 생성
     *
     * @param req 메뉴 생성 요청 body
     */
    void createMenu(MenuCreateRequestDTO req);

    /**
     * 메뉴의 카테고리 생성
     *
     * @param req 메뉴 카테고리 생성 요청 body
     */
    void createCategory(CategoryCreateRequestDTO req);
}