package io.plani.cafe.planicafe.api.payment;

import io.plani.cafe.planicafe.api.payment.dto.PaymentConfirmRequestDTO;
import io.plani.cafe.planicafe.domain.payment.service.PaymentService;
import io.plani.cafe.planicafe.global.common.response.ApiResponse;
import io.plani.cafe.planicafe.global.enums.SuccessCode;
import io.plani.cafe.planicafe.global.security.oauth2.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/confirm")
    public ResponseEntity<ApiResponse<Void>> confirmPayment(
            @AuthenticationPrincipal CustomOAuth2User user,
            @RequestBody PaymentConfirmRequestDTO req) {

        Long memberId = user.memberId();

        // 비즈니스 로직 호출
        paymentService.chargePoint(
                memberId,
                req.paymentKey(),
                req.referenceNo(),
                req.amount());

        // 성공 응답 반환
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.CHARGE_SUCCESS, null));
    }
}
