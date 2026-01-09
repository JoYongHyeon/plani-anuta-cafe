package io.plani.cafe.planicafe.domain.menu.repository;

import io.plani.cafe.planicafe.domain.menu.entity.MenuOptionItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuOptionItemRepository extends JpaRepository<MenuOptionItemEntity, Long> {
}
