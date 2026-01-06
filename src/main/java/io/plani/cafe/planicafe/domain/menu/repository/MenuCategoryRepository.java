package io.plani.cafe.planicafe.domain.menu.repository;

import io.plani.cafe.planicafe.domain.menu.entity.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Long> {
}
