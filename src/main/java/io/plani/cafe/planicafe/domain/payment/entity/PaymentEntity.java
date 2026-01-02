package io.plani.cafe.planicafe.domain.payment.entity;

import io.plani.cafe.planicafe.domain.member.entity.MemberEntity;
import io.plani.cafe.planicafe.global.entity.BaseEntity;
import io.plani.cafe.planicafe.global.enums.PaymentMethod;
import io.plani.cafe.planicafe.global.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Table(name = "payment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @Comment("충전된 포인트 수량")
    private int pointAmount;

    @Comment("실제 결제 금액(현금)")
    private int price;

    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(name = "payment_Key")
    @Comment("토스 paymentKey (외부 결제 키)")
    private String paymentKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @Builder
    public PaymentEntity(MemberEntity member,
                         int price,
                         PaymentMethod method,
                         String paymentKey) {

        this.member      = member;
        this.price       = price;
        this.method      = method;
        this.paymentKey  = paymentKey;
        this.pointAmount = price / 100; // "100원당 1포인트"
        this.status      = PaymentStatus.DONE;
    }
}
