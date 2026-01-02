package io.plani.cafe.planicafe.api.payment.dto;

public record PaymentConfirmRequestDTO(
        // 토스 결제 고유 키
        String paymentKey,
        // 우리 시스템의 주문 번호 (Toss 의 orderId)
        String referenceNo,
        // 실제 결제 금액
        int amount
) {}
