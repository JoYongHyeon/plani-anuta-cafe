package io.plani.cafe.planicafe.global.enums;

public enum PaymentStatus {

    // 결제 대기
    READY,
    // 결제 완료 (승인 성공)
    DONE,
    // 결제 취소
    CANCELED,
    // 결제 실패/중단
    ABORTED
}
