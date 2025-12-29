package io.plani.cafe.planicafe.security.service;

import io.plani.cafe.planicafe.common.enums.UserStatus;
import io.plani.cafe.planicafe.security.oauth2.*;
import io.plani.cafe.planicafe.user.entity.UserEntity;
import io.plani.cafe.planicafe.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
@Comment("로그인 시 DB 처리 핵심")
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest req) {

        // 1. 기본 구현으로부터 OAuth2User 가져오기 (구글 응답 값)
        OAuth2User oAuth2User = super.loadUser(req);

        // 2. 어떤 provider(google, naver...) 인지
        String provider = req.getClientRegistration().getRegistrationId();

        // 3. provider 에 맞는 UserInfo 구현체 생성
        OAuth2UserInfo userInfo =
                OAuth2UserInfoFactory.create(provider, oAuth2User.getAttributes());

        // 4. provider + providerId 로 DB 조회
        UserEntity user = userRepository.findByProviderAndProviderId(
                provider, userInfo.getId()
        // 없으면 회원 가입
        ).orElseGet(() -> register(provider, userInfo));

        // 5. 마지막 로그인 시간 갱신
        updateLastLogin(user);

        // 6. Security 에서 사용할 CustomerOAuth2User 생성하여 반환
        return new CustomOAuth2User(
                user.getId(),
                user.getEmail(),
                user.getRole(),
                oAuth2User.getAttributes()
        );
    }

    // 신규 사용자 등록 (최초 로그인 시)
    private UserEntity register(String provider, OAuth2UserInfo info) {

        UserEntity user = UserEntity.builder()
                .provider(provider)
                .providerId(info.getId())
                .email(info.getEmail())
                .name(info.getName())
                .profileImageUrl(info.getPicture())
                .status(UserStatus.ACTIVE)
                .role("ROLE_USER")
                .build();

        return userRepository.save(user);
    }


    // 마지막 로그인 시간만 갱신
    private void updateLastLogin(UserEntity user) {
        user.updateLastLogin(LocalDateTime.now());
        userRepository.save(user);
    }
}
