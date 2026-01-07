package io.plani.cafe.planicafe.domain.menu.service.impl;

import io.plani.cafe.planicafe.api.menu.dto.CategoryCreateRequestDTO;
import io.plani.cafe.planicafe.api.menu.dto.MenuCreateRequestDTO;
import io.plani.cafe.planicafe.domain.menu.entity.MenuCategory;
import io.plani.cafe.planicafe.domain.menu.entity.MenuEntity;
import io.plani.cafe.planicafe.domain.menu.repository.MenuCategoryRepository;
import io.plani.cafe.planicafe.domain.menu.repository.MenuRepository;
import io.plani.cafe.planicafe.domain.menu.service.MenuService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuServiceImpl implements MenuService {

    private final MenuRepository repository;
    private final MenuCategoryRepository categoryRepository;

    /**
     * @see MenuService#createMenu(MenuCreateRequestDTO)
     */
    @Override
    @Transactional
    public void createMenu(MenuCreateRequestDTO req) {

        MenuCategory category = categoryRepository.findById(req.categoryId())
                .orElseThrow(() -> new IllegalArgumentException("메뉴 카테고리를 찾을 수 없습니다."));

        MenuEntity menu = MenuEntity.builder()
                .category(category)
                .name(req.name())
                .price(req.price())
                .displayOrder(req.displayOrder())
                .build();

        repository.save(menu);
    }

    /**
     * @see MenuService#createCategory(CategoryCreateRequestDTO)
     */
    @Override
    @Transactional
    public void createCategory(CategoryCreateRequestDTO req) {

        MenuCategory category = MenuCategory.builder()
                .name(req.name())
                .displayOrder(req.displayOrder())
                .build();

        categoryRepository.save(category);
    }
}
