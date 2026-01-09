package io.plani.cafe.planicafe.api.menu;

import io.plani.cafe.planicafe.api.menu.dto.*;
import io.plani.cafe.planicafe.domain.menu.service.MenuService;
import io.plani.cafe.planicafe.global.common.response.ApiResponse;
import io.plani.cafe.planicafe.global.enums.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService service;

    /* ----------------- menu ----------------- */
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createMenu(@RequestBody MenuCreateRequestDTO req) {
        service.createMenu(req);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(SuccessCode.MENU_CREATE_SUCCESS, null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MenuDTO>> readMenu(@PathVariable Long id) {
        MenuDTO menu = service.readMenu(id);

        return ResponseEntity.ok(ApiResponse.success(SuccessCode.MENU_READ_SUCCESS, menu));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<Void>> updateMenu(@RequestBody MenuUpdateRequestDTO req) {
        service.updateMenu(req);

        return ResponseEntity.ok(ApiResponse.success(SuccessCode.MENU_UPDATE_SUCCESS, null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteMenu(@PathVariable Long id) {
        service.deleteMenu(id);

        return ResponseEntity.ok(ApiResponse.success(SuccessCode.MENU_DELETE_SUCCESS, null));
    }

    /* ----------------- option-group ----------------- */
    @PostMapping("/option-group")
    public ResponseEntity<ApiResponse<Void>> createOptionGroup(@RequestBody OptionGroupCreateRequestDTO req) {
        service.createOptionGroup(req);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(SuccessCode.MENU_GROUP_CREATE_SUCCESS, null));
    }

    @GetMapping("/option-group/{id}")
    public ResponseEntity<ApiResponse<OptionGroupDTO>> readOptionGroup(@PathVariable Long id) {
        OptionGroupDTO optionGroup = service.readOptionGroup(id);

        return ResponseEntity.ok(ApiResponse.success(SuccessCode.MENU_GROUP_READ_SUCCESS, optionGroup));
    }

    @PutMapping("/option-group")
    public ResponseEntity<ApiResponse<Void>> updateOptionGroup(@RequestBody OptionGroupUpdateRequestDTO req) {
        service.updateOptionGroup(req);

        return ResponseEntity.ok(ApiResponse.success(SuccessCode.MENU_GROUP_UPDATE_SUCCESS, null));
    }

    @DeleteMapping("/option-group/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteOptionGroup(@PathVariable Long id) {
        service.deleteOptionGroup(id);

        return ResponseEntity.ok(ApiResponse.success(SuccessCode.MENU_GROUP_DELETE_SUCCESS, null));
    }

    /* ----------------- option-item ----------------- */
    @PostMapping("/option-item")
    public ResponseEntity<ApiResponse<Void>> createOptionItem(@RequestBody OptionItemCreateRequestDTO req) {
        service.createOptionItem(req);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(SuccessCode.MENU_ITEM_CREATE_SUCCESS, null));
    }

    @GetMapping("/option-item/{id}")
    public ResponseEntity<ApiResponse<OptionItemDTO>> readOptionItem(@PathVariable Long id) {
        OptionItemDTO optionItem = service.readOptionItem(id);

        return ResponseEntity.ok(ApiResponse.success(SuccessCode.MENU_ITEM_READ_SUCCESS, optionItem));
    }

    @PutMapping("/option-item")
    public ResponseEntity<ApiResponse<Void>> updateOptionItem(@RequestBody OptionItemUpdateRequestDTO req) {
        service.updateOptionItem(req);

        return ResponseEntity.ok(ApiResponse.success(SuccessCode.MENU_ITEM_UPDATE_SUCCESS, null));
    }

    @DeleteMapping("/option-item/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteOptionItem(@PathVariable Long id) {
        service.deleteOptionItem(id);

        return ResponseEntity.ok(ApiResponse.success(SuccessCode.MENU_ITEM_DELETE_SUCCESS, null));
    }

    /* ----------------- category ----------------- */
    @PostMapping("/category")
    public ResponseEntity<ApiResponse<Void>> createCategory(@RequestBody CategoryCreateRequestDTO req) {
        service.createCategory(req);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(SuccessCode.MENU_CATEGORY_CREAT_SUCCESS, null));
    }
}
