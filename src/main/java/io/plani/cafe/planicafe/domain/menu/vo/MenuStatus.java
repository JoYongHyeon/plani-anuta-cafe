package io.plani.cafe.planicafe.domain.menu.vo;

import lombok.Getter;

@Getter
public enum MenuStatus {

    // 재고 있음
    IN_STOCK {
        public boolean canDisplay() {return true;}
        public boolean canOrder() {return true;}
    },

    // 재고 없음
    SOLD_OUT {
        public boolean canDisplay() {return true;}
        public boolean canOrder() {return false;}
    },

    // 메뉴 숨김
    BLIND {
        public boolean canDisplay() {return false;}
        public boolean canOrder() {return false;}
    };

    // 화면 표시 여부
    public abstract boolean canDisplay();

    // 주문 가능 여부
    public abstract boolean canOrder();
}
