package io.plani.cafe.planicafe.domain.menu.service;

import io.plani.cafe.planicafe.api.menu.dto.*;

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
     * @param id 메뉴 조회 요청 id
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
     * 옵션그룹 생성
     *
     * @param req 메뉴 옵션그룹 생성 요청 body
     */
    void createOptionGroup(OptionGroupCreateRequestDTO req);

    /**
     * 옵션그룹 조회
     *
     * @param id 메뉴 옵션그룹 조회 요청 id
     */
    OptionGroupDTO readOptionGroup(Long id);

    /**
     * 옵션그룹 수정
     *
     * @param req 메뉴 옵션그룹 수정 요청 body
     */
    void updateOptionGroup(OptionGroupUpdateRequestDTO req);

    /**
     * 옵션그룹 삭제
     *
     * @param id 메뉴 옵션그룹 삭제 요청 id
     */
    void deleteOptionGroup(Long id);

    /**
     * 옵션아이템 생성
     *
     * @param req 메뉴 옵션아이템 생성 요청 body
     */
    void createOptionItem(OptionItemCreateRequestDTO req);

    /**
     * 옵션아이템 조회
     *
     * @param id 메뉴 옵션아이템 조회 요청 id
     */
    OptionItemDTO readOptionItem(Long id);

    /**
     * 옵션아이템 수정
     *
     * @param req 메뉴 옵션아이템 수정 요청 body
     */
    void updateOptionItem(OptionItemUpdateRequestDTO req);

    /**
     * 옵션아이템 삭제
     *
     * @param id 메뉴 옵션아이템 삭제 요청 id
     */
    void deleteOptionItem(Long id);

    /**
     * 카테고리 생성
     *
     * @param req 메뉴 카테고리 생성 요청 body
     */
    void createCategory(CategoryCreateRequestDTO req);
}