package io.plani.cafe.planicafe.security.filter;

import io.plani.cafe.planicafe.security.jwt.JwtProvider;
import io.plani.cafe.planicafe.user.entity.UserEntity;
import io.plani.cafe.planicafe.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Comment("매 요청마다 JWT 검사")
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest req,
            @NonNull HttpServletResponse res,
            @NonNull FilterChain chain) throws ServletException, IOException {

        // 1. Authorization 헤더에서 JWT 추출
        String jwt = resolveToken(req);

        // 2. 토큰이 있고 유효하면 SecurityContext 에 인증정보 세팅
        if (StringUtils.hasText(jwt) && jwtProvider.validate(jwt)) {

            Long userId = jwtProvider.getUserId(jwt);

            UserEntity user = userRepository.findById(userId).
                    orElse(null);

            if (user != null) {
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                user, null, List.of(user::getRole)
                        );

                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        // 다음 필터로 진행
        chain.doFilter(req, res);
    }

    // "Bearer xxx" 형태에서 실제 토큰 부분만 추출
    private String resolveToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}
