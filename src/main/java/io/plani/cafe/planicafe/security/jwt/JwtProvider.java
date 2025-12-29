package io.plani.cafe.planicafe.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.plani.cafe.planicafe.common.config.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;


@Component
@RequiredArgsConstructor
@Comment("토큰 생성/검증")
public class JwtProvider {

    private final JwtProperties props;
    private SecretKey key;

    // secret 문자열을 HMAC SHA 키로 변환 (1회 생성 후 재사용)
    private SecretKey getKey() {
        if (key == null) {
            key = Keys.hmacShaKeyFor(props.secret().getBytes(StandardCharsets.UTF_8));
        }
        return key;
    }

    // Access Token 생성 (사용자 ID, 이메일, 권한 포함)
    public String createAccessToken(Long id, String email, String role) {
        long now = System.currentTimeMillis();
        Date exp = new Date(now + props.accessTokenExpiration());

        return Jwts.builder()
                // sub 클레임
                .subject(String.valueOf(id))
                // 커스텀 클레임
                .claim("email", email)
                .claim("role", role)
                .expiration(exp)
                .signWith(getKey())
                .compact();
    }

    // Refresh Token 생성 (userId 만 보관)
    public String createRefreshToken(Long id) {
        long now = System.currentTimeMillis();
        Date exp = new Date(now + props.refreshTokenExpiration());

        return Jwts.builder()
                .subject(String.valueOf(id))
                .expiration(exp)
                .signWith(getKey())
                .compact();
    }

    // 토큰에서 userId(subnet) 추출
    public Long getUserId(String token) {
        return Long.parseLong(
                Jwts.parser().verifyWith(getKey()).build()
                        .parseSignedClaims(token)
                        .getPayload().getSubject()
        );
    }

    // 토큰 유형성 검증 (서명/만료 등)
    public boolean validate(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            // 만료, 잘못된 서명 등은 여기로 들어옴
            return false;
        }
    }
}
