package io.plani.cafe.planicafe.global.security.oauth2;

import io.plani.cafe.planicafe.domain.member.vo.UserRole;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {

    private final Long id;
    private final String email;
    private final UserRole role;
    private final Map<String, Object> attributes;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // ROLE_USER, ROLE_ADMIN 등 권한 (String 으로 변환)
        return List.of(new SimpleGrantedAuthority(role.getAuthority()));
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return String.valueOf(id);
    }
}
