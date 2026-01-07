package io.plani.cafe.planicafe.domain.menu.repository;

import io.plani.cafe.planicafe.domain.menu.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<MenuEntity, Long> {
}
