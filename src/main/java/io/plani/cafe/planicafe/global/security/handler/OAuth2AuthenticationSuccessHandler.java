package io.plani.cafe.planicafe.global.security.handler;

import io.plani.cafe.planicafe.global.config.OAuth2Properties;
import io.plani.cafe.planicafe.global.security.jwt.JwtProvider;
import io.plani.cafe.planicafe.global.security.oauth2.CustomOAuth2User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Comment("성공 핸들러 - JWT 발급 + 리다이렉트")
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    private final OAuth2Properties props;

    /**
     * 언제 호출되는가?
     * - OAuth2 로그인 전체 과정이 성공적으로 끝난 직후
     * - CustomOAuth2UserService.loadUser() 반환 → 인증 성공 → 여기 호출됨
     *
     * 무엇을 하는가?
     * 1) 인증된 사용자 정보(CustomOAuth2User) 조회
     * 2) AccessToken / RefreshToken 생성
     * 3) 토큰을 쿼리스트링에 실어서 프론트 redirect
     */
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest req,
            HttpServletResponse res,
            Authentication auth) throws IOException {

        // 인증된 사용자 정보 (loadUser() 에서 만든 사용자 객체)
        CustomOAuth2User principal = (CustomOAuth2User) auth.getPrincipal();

        // 1. 토큰 생성
        String access = jwtProvider.createAccessToken(
                principal.getId(),
                principal.getEmail(),
                principal.getRole().getAuthority()
        );

        String refresh = jwtProvider.createRefreshToken(principal.getId());

        /**
         * 2. Refresh Token 을 쿠키에 담음
         * - HttpOnly: XSS 방지
         * - Secure: HTTPS에서만 전송
         * - sameSite=Lax: CSRF 방어
         */
        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", refresh)
                .httpOnly(true)
//                .secure(true) // TODO: 실서비스에서는 true, 로컬 HTTP 면 false 고려
                .secure(false)
                .path("/")
                .maxAge(7 * 24 * 60 * 60)
                .sameSite("Lax")
                .build();

        res.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

        //
        /**
         * 3. Access TOken 은 프론트엔드가 즉시 사용할 수 있도록 URL에 실어보냄
         *    (Access Token 은 수명이 짧으므로 비교적 안전, 혹은 이조차 쿠키에 담아도 됨)
         */
        String redirect = UriComponentsBuilder.fromUriString(props.redirectUri())
                .queryParam("accessToken", access)
                .build().toUriString();

        // 4. 프론트엔드로 최종 이동
        getRedirectStrategy().sendRedirect(req, res, redirect);
    }
}
