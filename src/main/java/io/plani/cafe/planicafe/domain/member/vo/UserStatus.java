package io.plani.cafe.planicafe.domain.member.vo;

import lombok.Getter;

@Getter
public enum UserStatus {

    // 정상 계정 : 로그인 및 주문 가능
    ACTIVE {
        public boolean canLogin() {return true;}
        public boolean canOrder() {return true;}
    },
    // 차단 계정 : 로그인 및 주문 모두 불가
    BLOCKED {
        public boolean canLogin() {return false;}
        public boolean canOrder() {return false;}

    },
    // 탈퇴 계정 : 모든 행위 불가 (이력 보존용)
    WITHDRAWN{
        public boolean canLogin() {return false;}
        public boolean canOrder() {return false;}
    };

    // 로그인 가능 여부
    public abstract boolean canLogin();

    // 주문 가능 여부
    public abstract boolean canOrder();
}
