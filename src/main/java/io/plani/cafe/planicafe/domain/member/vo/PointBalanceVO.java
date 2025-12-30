package io.plani.cafe.planicafe.domain.member.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable // 정의
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointBalanceVO {

    @Column(name = "point_balance", nullable = false)
    private int value;

    /**
     * 재화 생성
     * @param value 초기 재화 값 (0 이상)
     */
    public PointBalanceVO(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("재화는 음수가 될 수 없습니다.");
        }
        this.value = value;
    }

    /**
     * 재화 증가
     * @param amount 증가량 (양수)
     * @return 증가된 새로운 PointBalanceVO
     */
    public PointBalanceVO add(int amount) {
        return new PointBalanceVO(this.value + amount);
    }

    /**
     * 재화 차감
     * @param amount 차감량 (양수)
     * @return 차감된 새로운 PointBalanceVO
     */
    public PointBalanceVO subtract(int amount) {
        if (this.value < amount) {
            throw new IllegalArgumentException("잔액 부족");
        }
        return new PointBalanceVO(this.value - amount);
    }

    /**
     * 잔액 여부 확인
     */
    public boolean isEnough(int amount) {
        return this.value >= amount;
    }
}
