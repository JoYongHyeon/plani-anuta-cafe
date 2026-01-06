package io.plani.cafe.planicafe.domain.menu.service.impl;

import io.plani.cafe.planicafe.domain.menu.entity.MenuCategory;
import io.plani.cafe.planicafe.domain.menu.entity.MenuEntity;
import io.plani.cafe.planicafe.domain.menu.repository.MenuCategoryRepository;
import io.plani.cafe.planicafe.domain.menu.repository.MenuRepository;
import io.plani.cafe.planicafe.domain.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository repository;
    private final MenuCategoryRepository categoryRepository;

    /**
     * @see MenuService#createMenu(Long, String, int, int)
     */
    @Override
    public void createMenu(Long categoryId, String menuName, int price, int displayOrder) {

        MenuCategory category = categoryRepository.findById(categoryId).orElseThrow();

        MenuEntity menu = MenuEntity.builder()
                .category(category)
                .name(menuName)
                .price(price)
                .displayOrder(displayOrder)
                .build();

        repository.save(menu);
    }

    /**
     * @see MenuService#createCategory(String, int) 
     */
    @Override
    public void createCategory(String categoryName, int displayOrder) {

        MenuCategory category = MenuCategory.builder()
                .name(categoryName)
                .displayOrder(displayOrder)
                .build();

        categoryRepository.save(category);
    }
}
