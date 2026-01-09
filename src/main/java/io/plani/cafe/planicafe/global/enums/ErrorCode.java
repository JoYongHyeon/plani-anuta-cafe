package io.plani.cafe.planicafe.global.enums;

import io.plani.cafe.planicafe.global.common.response.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode implements ResponseCode {

    // 공통 에러
    INVALID_INPUT_VALUE(400, "COMMON-400", "올바르지 않은 입력값입니다."),
    INTERNAL_SERVER_ERROR(500, "COMMON-500", "서버 내부 오류입니다."),

    // 결제 에러
    PAYMENT_CONFIRM_FAILED(400, "PAY-400", "결제 승인에 실패했습니다."),

    // 메뉴 에러
    MENU_NOT_FOUND(404, "MENU-400", "메뉴를 찾을 수 없습니다."),
    MENU_OPTION_GROUP_NOT_FOUND(404, "MENU-400", "메뉴 옵션그룹을 찾을 수 없습니다."),
    MENU_OPTION_ITEM_NOT_FOUND(404, "MENU-400", "메뉴 옵션아이템을 찾을 수 없습니다."),
    MENU_CATEGORY_NOT_FOUND(404, "MENU-400", "메뉴 카테고리를 찾을 수 없습니다.");

    private final int httpStatus;
    private final String code;
    private final String message;
}
