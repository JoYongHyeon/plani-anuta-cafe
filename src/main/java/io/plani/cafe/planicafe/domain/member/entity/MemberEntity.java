package io.plani.cafe.planicafe.domain.member.entity;

import io.plani.cafe.planicafe.domain.member.vo.PointBalanceVO;
import io.plani.cafe.planicafe.domain.member.vo.UserRole;
import io.plani.cafe.planicafe.global.entity.BaseEntity;
import io.plani.cafe.planicafe.domain.member.vo.UserStatus;
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
public class MemberEntity extends BaseEntity {

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Comment("계정 상태")
    private UserStatus status;

    @Embedded // 사용
    @Comment("보유 재화")
    private PointBalanceVO pointBalance;

    @Column(name = "last_login_at")
    @Comment("마지막 로그인 일시")
    private LocalDateTime lastLoginAt;

    @Column(name = "profile_image_url")
    @Comment("프로필 이미지 URL")
    private String profileImageUrl;


    public void validateLogin() {
        if (!status.canLogin()) {
            throw new IllegalStateException("로그인 불가능한 계정 상태입니다.");
        }
    }

    public void updateLastLogin(LocalDateTime now) {
        this.lastLoginAt = now;
    }
}
