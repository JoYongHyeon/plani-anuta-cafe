package io.plani.cafe.planicafe.domain.payment.entity;

import io.plani.cafe.planicafe.global.entity.BaseEntity;
import io.plani.cafe.planicafe.global.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Table(name = "payment_transaction")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentTransactionEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Comment("증감 포인트 양 (충전+, 사용-)")
    private int amount;

    @Comment("거래 직후 잔액")
    private int balanceAfter;

    @Column(name = "reference_no")
    @Comment("외부 거래 식별자 (Toss orderId 등)")
    private String referenceNo;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private PaymentEntity payment; // 충전일 때만 존재

    @Builder
    public PaymentTransactionEntity(PaymentEntity payment,
                                    TransactionType type,
                                    int amount,
                                    int balanceAfter,
                                    String referenceNo,
                                    String description) {
        this.payment      = payment;
        this.type         = type;
        this.amount       = amount;
        this.balanceAfter = balanceAfter;
        this.referenceNo  = referenceNo;
        this.description  = description;
    }
}
