package io.plani.cafe.planicafe.infrastructure.payment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

/**
 * 토스페이먼츠 API 인증 및 승인 요청 클라이언트
 * @see <a href="https://docs.tosspayments.com/reference/using-api/authorization">인증 가이드</a>
 */
@Component
public class TossPaymentClient {

    private final RestClient restClient;

    public TossPaymentClient(@Value("${toss.secret-key}") String secretKey) {
        // 토스 인증 규격: "시크릿키:"를 Base64 인코딩하여 Basic 인증 헤더로 사용
        String encodedKey = Base64.getEncoder().
                encodeToString((secretKey + ":").getBytes(StandardCharsets.UTF_8));

        this.restClient = RestClient.builder()
                .baseUrl("https://api.tosspayments.com/v1/payments")
                .defaultHeader("Authorization", "Basic " + encodedKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    /**
     * 토스 서버에 결제 승인을 요청함 (Confirm API)
     */
    public void confirm(String paymentKey, String referenceNo, int amount) {
        var body = Map.of(
                "paymentKey", paymentKey,
                "orderId", referenceNo,
                "amount", amount
        );

        restClient.post()
                .uri("/confirm")
                // 멱등 키 권장: 멱등 키를 추가하여 네트워크 재시도 시 중복 결제를 방지
                .header("Idempotency-key", referenceNo)
                .body(body)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    throw new RuntimeException("토스 결제 승인 실패: " + res.getStatusText());
                })
                .toBodilessEntity();
    }
}
