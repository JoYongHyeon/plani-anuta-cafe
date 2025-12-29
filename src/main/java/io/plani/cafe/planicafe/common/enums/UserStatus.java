package io.plani.cafe.planicafe.common.enums;

import lombok.Getter;

@Getter
public enum UserStatus {
    // 정상 이용중
    ACTIVE,
    // 휴먼
    INACTIVE,
    // 차단
    BLOCKED
}
