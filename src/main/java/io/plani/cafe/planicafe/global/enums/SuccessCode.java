package io.plani.cafe.planicafe.global.enums;

import io.plani.cafe.planicafe.global.common.response.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessCode implements ResponseCode {

    PAYMENT_SUCCESS(200, "PAY-200", "결제가 완료되었습니다."),
    CHARGE_SUCCESS(200, "PAY-201", "포인트 충전이 성공했습니다."),

    // MENU
    MENU_CREATE_SUCCESS(201, "MENU-200", "메뉴 생성에 성공했습니다."),
    MENU_READ_SUCCESS(200, "MENU-200", "메뉴 조회에 성공했습니다."),
    MENU_UPDATE_SUCCESS(200, "MENU-200", "메뉴 수정에 성공했습니다."),
    MENU_DELETE_SUCCESS(200, "MENU-200", "메뉴 삭제에 성공했습니다."),

    MENU_GROUP_CREATE_SUCCESS(201, "MENU-200", "메뉴 옵션그룹 생성에 성공했습니다."),
    MENU_GROUP_READ_SUCCESS(200, "MENU-200", "메뉴 옵션그룹 조회에 성공했습니다."),
    MENU_GROUP_UPDATE_SUCCESS(200, "MENU-200", "메뉴 옵션그룹 수정에 성공했습니다."),
    MENU_GROUP_DELETE_SUCCESS(200, "MENU-200", "메뉴 옵션그룹 삭제에 성공했습니다."),

    MENU_ITEM_CREATE_SUCCESS(201, "MENU-200", "메뉴 옵션아이템 생성에 성공했습니다."),
    MENU_ITEM_READ_SUCCESS(200, "MENU-200", "메뉴 옵션아이템 조회에 성공했습니다."),
    MENU_ITEM_UPDATE_SUCCESS(200, "MENU-200", "메뉴 옵션아이템 수정에 성공했습니다."),
    MENU_ITEM_DELETE_SUCCESS(200, "MENU-200", "메뉴 옵션아이템 삭제에 성공했습니다."),

    MENU_CATEGORY_CREAT_SUCCESS(201, "MENU-204", "카테고리 생성이 성공했습니다.");

    private final int httpStatus;
    private final String code;
    private final String message;
}
