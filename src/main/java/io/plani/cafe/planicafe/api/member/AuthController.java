package io.plani.cafe.planicafe.api.member;

import io.plani.cafe.planicafe.domain.member.entity.MemberEntity;
import io.plani.cafe.planicafe.domain.member.repository.MemberRepository;
import io.plani.cafe.planicafe.global.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * [인증 전담 컨트롤러]
 * - 주요 역할: Access Token 만료 시 Refresh Token 을 통한 재발급 처리
 * - 호출 주체: 프론트엔드 Axios/Fetch Interceptor (401 에러 응답 시 자동 호출)
 */

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    /**
     * Access Token 재발급 (Silent Refresh)
     * @param refreshToken 브라우저 쿠키에 저장된 HttpOnly 리프레시 토큰
     * @return 새로운 Access Token (Json 바디)
     */
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(
            @CookieValue(name = "refreshToken", required = false) String refreshToken) {

        // 1. 쿠키에 Refresh Token 자체가 없는 경우 (로그인 안 됨 혹은 만료 후 삭제됨)
        if (refreshToken == null) {
            log.warn("[Auth] 재발급 실패: 쿠키에 리프레시 토큰이 없음");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("code", "NO_REFRESH_TOKEN", "message", "다시 로그인해주세요."));
        }

        // 2. Refresh Token 유효성 및 만료 여부 검사 (JwtProvider 내부 로직)
        try {
            if (!jwtProvider.validate(refreshToken)) {
                return buildErrorResponse("REFRESH_TOKEN_EXPIRED", "세션이 만료되었습니다. 다시 로그인해주세요.");
            }
        } catch (Exception e) {
            log.error("[Auth] 리프레시 토큰 검증 중 오류 발생: {}", e.getMessage());
            return buildErrorResponse("INVALID_REFRESH_TOKEN", "유효하지 않은 토큰입니다.");
        }

        // 3. 토큰에서 유저 ID(PK) 추출
        Long userId = jwtProvider.getUserId(refreshToken);

        // 4. DB 유저 존재 여부 최종 확인 (보안 및 데이터 일관성)
        MemberEntity member = memberRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("[Auth] 재발급 실패: ID {} 에 해당하는 유저가 DB에 없음", userId);
                    return new RuntimeException("존재하지 않는 사용자입니다.");
                });

        // 5. 새로운 Access Token 생성
        String newAccessToken = jwtProvider.createAccessToken(
                member.getId(),
                member.getEmail(),
                member.getRole().getAuthority()
        );

        log.info("[Auth] Access Token 재발급 성공: 유저 ID {}", userId);

        // 6. 성공 응답 (Access Token 은 Json 바디로 전달)
        return ResponseEntity.ok(Map.of("access_token", newAccessToken));
    }


    /**
     * 일관된 에러 응답 생성을 위한 헬퍼 메서드
     */
    private ResponseEntity<?> buildErrorResponse(String code, String message) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("code", code, "message", message));
    }
}
