package io.plani.cafe.planicafe.domain.member.entity;

import io.plani.cafe.planicafe.domain.member.vo.PointBalanceVO;
import io.plani.cafe.planicafe.domain.member.vo.UserRole;
import io.plani.cafe.planicafe.domain.payment.entity.PaymentEntity;
import io.plani.cafe.planicafe.domain.payment.entity.PaymentTransactionEntity;
import io.plani.cafe.planicafe.global.entity.BaseEntity;
import io.plani.cafe.planicafe.domain.member.vo.UserStatus;
import io.plani.cafe.planicafe.global.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false)
    @Comment("OAuth2 공급자")
    private String provider;

    @Column(nullable = false, unique = true)
    @Comment("공급자에서 제공하는 사용자 ID (sub)")
    private String providerId;

    private String name;
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Comment("계정 상태")
    private UserStatus status;

    @Embedded // 사용
    @Comment("보유 재화")
    private PointBalanceVO pointBalance;

    @Column(name = "last_login_at")
    @Comment("마지막 로그인 일시")
    private LocalDateTime lastLoginAt;

    @Column(name = "profile_image_url")
    @Comment("프로필 이미지 URL")
    private String profileImageUrl;


    public void completeLoginSession() {
        this.validateLogin();
        this.updateLastLogin();
    }

    public void validateLogin() {
        if (!status.canLogin()) {
            throw new IllegalStateException("로그인 불가능한 계정 상태입니다.");
        }
    }

    public void updateLastLogin() {
        this.lastLoginAt = LocalDateTime.now();
    }

    /**
     * 결제 정보를 바탕으로 포인트를 충전하고 거래 이력을 반환
     */
    public PaymentTransactionEntity chargePoint(PaymentEntity paymentEntity,
                                                String referenceNo) {

        // 1. VO 객체를 통해 포인트 증가
        this.pointBalance = this.pointBalance.add(paymentEntity.getPointAmount());

        // 2. 이력 생성 후 반환
        return PaymentTransactionEntity.builder()
                .payment(paymentEntity)
                .type(TransactionType.CHARGE)
                .amount(paymentEntity.getPointAmount())
                .balanceAfter(this.pointBalance.getValue())
                .referenceNo(referenceNo)
                .description("포인트 충전 (토스페이)")
                .build();
    }

    /**
     * 특정 목적(회원 가입)을 위한 전용 빌더 생성자
     */
    @Builder
    public MemberEntity(String provider,
                        String providerId,
                        String email,
                        String name) {
        this.provider = provider;
        this.providerId = providerId;
        this.email = email;
        this.name = name;

        // 시스템이 관리해야 할 초기값들
        this.role = UserRole.USER;
        this.status = UserStatus.ACTIVE;
        this.pointBalance = new PointBalanceVO(0);
    }
}
