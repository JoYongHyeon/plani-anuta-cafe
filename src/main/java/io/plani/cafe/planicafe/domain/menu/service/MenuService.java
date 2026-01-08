package io.plani.cafe.planicafe.domain.menu.service;

import io.plani.cafe.planicafe.api.menu.dto.CategoryCreateRequestDTO;
import io.plani.cafe.planicafe.api.menu.dto.MenuCreateRequestDTO;
import io.plani.cafe.planicafe.api.menu.dto.MenuDTO;
import io.plani.cafe.planicafe.api.menu.dto.MenuUpdateRequestDTO;

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
     * 메뉴 조회
     *
     * @param id 메뉴 생성 요청 id
     */
    MenuDTO readMenu(Long id);

    /**
     * 메뉴 조회
     *
     * @param req 메뉴 수정 요청 body
     */
    void updateMenu(MenuUpdateRequestDTO req);

    /**
     * 메뉴 삭제
     *
     * @param id 메뉴 삭제 요청 id
     */
    void deleteMenu(Long id);

    /**
     * 메뉴의 카테고리 생성
     *
     * @param req 메뉴 카테고리 생성 요청 body
     */
    void createCategory(CategoryCreateRequestDTO req);
}