package io.plani.cafe.planicafe.security.handler;

import io.plani.cafe.planicafe.common.config.OAuth2Properties;
import io.plani.cafe.planicafe.security.jwt.JwtProvider;
import io.plani.cafe.planicafe.security.oauth2.CustomOAuth2User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
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

        // 1. 인증된 사용자 정보 (loadUser() 에서 만든 사용자 객체)
        CustomOAuth2User principal = (CustomOAuth2User) auth.getPrincipal();

        // 2. Access / Refresh Token 생성
        String access = jwtProvider.createAccessToken(
                principal.getId(), principal.getEmail(), principal.getRole()
        );

        String refresh = jwtProvider.createRefreshToken(
                principal.getId()
        );

        // 3. 리다이렉트 URL 생성 (JWT 포함)
        String redirect = UriComponentsBuilder.fromUriString(props.redirectUri())
                .queryParam("accessToken", access)
                .queryParam("refreshToken", refresh)
                .build().toUriString();


        // 4. 프론트엔드로 최종 이동
        getRedirectStrategy().sendRedirect(req, res, redirect);


    }
}
