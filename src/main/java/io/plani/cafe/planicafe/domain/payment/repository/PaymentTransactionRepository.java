package io.plani.cafe.planicafe.domain.payment.repository;

import io.plani.cafe.planicafe.domain.payment.entity.PaymentTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

// 포인트 거래 이력 저장소
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransactionEntity, Long> {
}
