package io.plani.cafe.planicafe.domain.menu.service.impl;

import io.plani.cafe.planicafe.api.menu.dto.CategoryCreateRequestDTO;
import io.plani.cafe.planicafe.api.menu.dto.MenuCreateRequestDTO;
import io.plani.cafe.planicafe.api.menu.dto.MenuDTO;
import io.plani.cafe.planicafe.api.menu.dto.MenuUpdateRequestDTO;
import io.plani.cafe.planicafe.domain.menu.entity.MenuCategoryEntity;
import io.plani.cafe.planicafe.domain.menu.entity.MenuEntity;
import io.plani.cafe.planicafe.domain.menu.exception.MenuException;
import io.plani.cafe.planicafe.domain.menu.repository.MenuCategoryRepository;
import io.plani.cafe.planicafe.domain.menu.repository.MenuRepository;
import io.plani.cafe.planicafe.domain.menu.service.MenuService;
import io.plani.cafe.planicafe.global.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        MenuCategoryEntity category = categoryRepository.findById(req.categoryId())
                .orElseThrow(() -> new MenuException(ErrorCode.MENU_CATEGORY_NOT_FOUND));

        MenuEntity menu = MenuEntity.builder()
                .category(category)
                .name(req.name())
                .price(req.price())
                .displayOrder(req.displayOrder())
                .build();

        repository.save(menu);
    }

    /**
     * @see MenuService#readMenu(Long)
     */
    @Override
    public MenuDTO readMenu(Long id) {
        MenuEntity entity = repository.findById(id)
                .orElseThrow(() -> new MenuException(ErrorCode.MENU_NOT_FOUND));

        return MenuDTO.from(entity);
    }

    /**
     * @see MenuService#updateMenu(MenuUpdateRequestDTO) 
     */
    @Override
    @Transactional
    public void updateMenu(MenuUpdateRequestDTO req) {
        MenuEntity entity = repository.findById(req.id())
                .orElseThrow(() -> new MenuException(ErrorCode.MENU_NOT_FOUND));

        MenuCategoryEntity category = categoryRepository.findById(req.categoryId())
                .orElseThrow(() -> new MenuException(ErrorCode.MENU_CATEGORY_NOT_FOUND));

        entity.changeCategory(category);
        entity.changeName(req.name());
        entity.changePrice(req.price());
        entity.changeStatus(req.status());
        entity.changeDisplayOrder(req.displayOrder());
    }

    /**
     * @see MenuService#deleteMenu(Long) 
     */
    @Override
    @Transactional
    public void deleteMenu(Long id) {
        MenuEntity entity = repository.findById(id)
                .orElseThrow(() -> new MenuException(ErrorCode.MENU_NOT_FOUND));

        repository.delete(entity);
    }

    /**
     * @see MenuService#createCategory(CategoryCreateRequestDTO)
     */
    @Override
    @Transactional
    public void createCategory(CategoryCreateRequestDTO req) {

        MenuCategoryEntity category = MenuCategoryEntity.builder()
                .name(req.name())
                .displayOrder(req.displayOrder())
                .build();

        categoryRepository.save(category);
    }
}
