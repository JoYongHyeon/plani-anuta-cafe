package io.plani.cafe.planicafe.user.entity;

import io.plani.cafe.planicafe.common.entity.BaseEntity;
import io.plani.cafe.planicafe.common.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@Table(name = "users")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Comment("OAuth2 공급자")
    private String provider;

    @Column(nullable = false, unique = true)
    @Comment("공급자에서 제공하는 사용자 ID (sub)")
    private String providerId;

    private String name;
    private String email;

    @Column(nullable = false)
    @Comment("권한 (예: ROLE_USER, ROLE_ADMIN)")
    private String role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Comment("사용자 상태")
    private UserStatus status;

    @Column(name = "last_login_at")
    @Comment("마지막 로그인 일시")
    private LocalDateTime lastLoginAt;

    @Column(name = "profile_image_url")
    @Comment("프로필 이미지 URL")
    private String profileImageUrl;


    public void updateLastLogin(LocalDateTime now) {
        this.lastLoginAt = now;
    }
}
