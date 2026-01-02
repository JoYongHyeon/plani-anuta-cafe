package io.plani.cafe.planicafe.domain.payment.repository;

import io.plani.cafe.planicafe.domain.payment.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

// 결제 원장 저장소
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
}
