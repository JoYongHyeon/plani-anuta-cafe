package io.plani.cafe.planicafe.user.repository;

import io.plani.cafe.planicafe.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // provider + providerId 로 OAuth2 사용자 조회
    Optional<UserEntity> findByProviderAndProviderId(String provider, String providerId);
}
