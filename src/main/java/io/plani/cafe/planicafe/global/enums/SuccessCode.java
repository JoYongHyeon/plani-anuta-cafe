package io.plani.cafe.planicafe.global.enums;

import io.plani.cafe.planicafe.global.common.response.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessCode implements ResponseCode {

    PAYMENT_SUCCESS(200, "PAY-200", "결제가 완료되었습니다."),
    CHARGE_SUCCESS(200, "PAY-201", "포인트 충전이 성공했습니다.");

    private final int httpStatus;
    private final String code;
    private final String message;
}
