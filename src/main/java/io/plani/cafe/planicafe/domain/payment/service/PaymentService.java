package io.plani.cafe.planicafe.domain.payment.service;

import io.plani.cafe.planicafe.domain.member.entity.MemberEntity;
import io.plani.cafe.planicafe.domain.member.repository.MemberRepository;
import io.plani.cafe.planicafe.domain.payment.entity.PaymentEntity;
import io.plani.cafe.planicafe.domain.payment.entity.PaymentTransactionEntity;
import io.plani.cafe.planicafe.domain.payment.repository.PaymentRepository;
import io.plani.cafe.planicafe.domain.payment.repository.PaymentTransactionRepository;
import io.plani.cafe.planicafe.global.enums.PaymentMethod;
import io.plani.cafe.planicafe.infrastructure.payment.TossPaymentClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final TossPaymentClient tossClient;
    private final MemberRepository memberRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentTransactionRepository paymentTransactionRepository;


    /**
     * 결제 승인 및 포인트 충전 프로세스
     * @Transactional 어노테이션을 통해 도메인 로직과 외부 API 통신의 정합성을 보장
     */
    @Transactional
    public void chargePoint(Long memberId,
                            String paymentKey,
                            String referenceNo,
                            int amount) {

        // 1. 회원 정보 조회
        MemberEntity member = memberRepository.findById(memberId).orElseThrow();

        // 2. 토스페이먼츠 최종 승인 요청 (멱등성 헤더 포함)
        tossClient.confirm(paymentKey, referenceNo, amount);

        // 3. 결제 엔티티 저장
        PaymentEntity paymentEntity = PaymentEntity.builder()
                .member(member)
                .price(amount)
                .method(PaymentMethod.TOSS)
                .paymentKey(paymentKey)
                .build();

        // 4. 포인트 충전 처리 및 거래 이력 생성
        PaymentTransactionEntity tx = member.chargePoint(paymentEntity, referenceNo);

        // 5. 최종 거래 이력 저장
        paymentTransactionRepository.save(tx);
    }
}
