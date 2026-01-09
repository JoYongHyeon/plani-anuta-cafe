package io.plani.cafe.planicafe.domain.menu.service.impl;

import io.plani.cafe.planicafe.api.menu.dto.*;
import io.plani.cafe.planicafe.domain.menu.entity.MenuCategoryEntity;
import io.plani.cafe.planicafe.domain.menu.entity.MenuEntity;
import io.plani.cafe.planicafe.domain.menu.entity.MenuOptionGroupEntity;
import io.plani.cafe.planicafe.domain.menu.entity.MenuOptionItemEntity;
import io.plani.cafe.planicafe.domain.menu.exception.MenuException;
import io.plani.cafe.planicafe.domain.menu.repository.MenuCategoryRepository;
import io.plani.cafe.planicafe.domain.menu.repository.MenuOptionGroupRepository;
import io.plani.cafe.planicafe.domain.menu.repository.MenuOptionItemRepository;
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
    private final MenuOptionGroupRepository optionGroupRepository;
    private final MenuOptionItemRepository optionItemRepository;

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
        MenuEntity menu = repository.findById(id)
                .orElseThrow(() -> new MenuException(ErrorCode.MENU_NOT_FOUND));

        return MenuDTO.from(menu);
    }

    /**
     * @see MenuService#updateMenu(MenuUpdateRequestDTO) 
     */
    @Override
    @Transactional
    public void updateMenu(MenuUpdateRequestDTO req) {
        MenuEntity menu = repository.findById(req.id())
                .orElseThrow(() -> new MenuException(ErrorCode.MENU_NOT_FOUND));

        if(!menu.getCategory().getId().equals(req.categoryId())){
            MenuCategoryEntity category = categoryRepository.findById(req.categoryId())
                    .orElseThrow(() -> new MenuException(ErrorCode.MENU_CATEGORY_NOT_FOUND));

            menu.changeCategory(category);
        }

        menu.changeName(req.name());
        menu.changePrice(req.price());
        menu.changeStatus(req.status());
        menu.changeDisplayOrder(req.displayOrder());
    }

    /**
     * @see MenuService#deleteMenu(Long) 
     */
    @Override
    @Transactional
    public void deleteMenu(Long id) {
        MenuEntity menu = repository.findById(id)
                .orElseThrow(() -> new MenuException(ErrorCode.MENU_NOT_FOUND));

        repository.delete(menu);
    }

    /**
     * @see MenuService#createOptionGroup(OptionGroupCreateRequestDTO)
     */
    @Override
    @Transactional
    public void createOptionGroup(OptionGroupCreateRequestDTO req) {
        MenuEntity menu = repository.findById(req.menuId())
                .orElseThrow(() -> new MenuException(ErrorCode.MENU_NOT_FOUND));

        MenuOptionGroupEntity optionGroup = MenuOptionGroupEntity.builder()
                .menu(menu)
                .name(req.name())
                .isRequired(req.isRequired())
                .displayOrder(req.displayOrder())
                .build();

        optionGroupRepository.save(optionGroup);
    }

    /**
     * @see MenuService#readOptionGroup(Long) 
     */
    @Override
    public OptionGroupDTO readOptionGroup(Long id) {
        MenuOptionGroupEntity optionGroup = optionGroupRepository.findById(id)
                .orElseThrow(() -> new MenuException(ErrorCode.MENU_OPTION_GROUP_NOT_FOUND));

        return OptionGroupDTO.from(optionGroup);
    }

    /**
     * @see MenuService#updateOptionGroup(OptionGroupUpdateRequestDTO) 
     */
    @Override
    @Transactional
    public void updateOptionGroup(OptionGroupUpdateRequestDTO req) {
        MenuOptionGroupEntity optionGroup = optionGroupRepository.findById(req.id())
                .orElseThrow(() -> new MenuException(ErrorCode.MENU_OPTION_GROUP_NOT_FOUND));

        optionGroup.changeName(req.name());
        optionGroup.changeIsRequired(req.isRequired());
        optionGroup.changeDisplayOrder(req.displayOrder());
    }

    /**
     * @see MenuService#deleteOptionGroup(Long)
     */
    @Override
    @Transactional
    public void deleteOptionGroup(Long id) {
        MenuOptionGroupEntity optionGroup = optionGroupRepository.findById(id)
                .orElseThrow(() -> new MenuException(ErrorCode.MENU_OPTION_GROUP_NOT_FOUND));

        optionGroupRepository.delete(optionGroup);
    }

    /**
     * @see MenuService#createOptionItem(OptionItemCreateRequestDTO)
     */
    @Override
    @Transactional
    public void createOptionItem(OptionItemCreateRequestDTO req) {
        MenuOptionGroupEntity optionGroup = optionGroupRepository.findById(req.groupId())
                .orElseThrow(() -> new MenuException(ErrorCode.MENU_OPTION_GROUP_NOT_FOUND));

        MenuOptionItemEntity optionItem = MenuOptionItemEntity.builder()
                .group(optionGroup)
                .name(req.name())
                .price(req.price())
                .displayOrder(req.displayOrder())
                .build();

        optionItemRepository.save(optionItem);
    }

    /**
     * @see MenuService#readOptionItem(Long)
     */
    @Override
    public OptionItemDTO readOptionItem(Long id) {
        MenuOptionItemEntity optionItem = optionItemRepository.findById(id)
                .orElseThrow(() -> new MenuException(ErrorCode.MENU_OPTION_ITEM_NOT_FOUND));

        return OptionItemDTO.from(optionItem);
    }

    /**
     * @see MenuService#updateOptionItem(OptionItemUpdateRequestDTO)
     */
    @Override
    @Transactional
    public void updateOptionItem(OptionItemUpdateRequestDTO req) {
        MenuOptionItemEntity optionItem = optionItemRepository.findById(req.id())
                .orElseThrow(() -> new MenuException(ErrorCode.MENU_OPTION_ITEM_NOT_FOUND));

        optionItem.changeName(req.name());
        optionItem.changePrice(req.price());
        optionItem.changeDisplayOrder(req.displayOrder());
    }

    /**
     * @see MenuService#deleteOptionItem(Long) 
     */
    @Override
    @Transactional
    public void deleteOptionItem(Long id) {
        MenuOptionItemEntity optionItem = optionItemRepository.findById(id)
                .orElseThrow(() -> new MenuException(ErrorCode.MENU_OPTION_ITEM_NOT_FOUND));

        optionItemRepository.delete(optionItem);
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
