package io.plani.cafe.planicafe.api.menu;

import io.plani.cafe.planicafe.api.menu.dto.CategoryCreateRequestDTO;
import io.plani.cafe.planicafe.api.menu.dto.MenuCreateRequestDTO;
import io.plani.cafe.planicafe.domain.menu.service.MenuService;
import io.plani.cafe.planicafe.global.common.response.ApiResponse;
import io.plani.cafe.planicafe.global.enums.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService service;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createMenu(@RequestBody MenuCreateRequestDTO req) {
        service.createMenu(
                req.categoryId(),
                req.name(),
                req.price(),
                req.displayOrder());

        return ResponseEntity.ok(ApiResponse.success(SuccessCode.MENU_CREAT_SUCCESS, null));
    }

    @PostMapping("/category")
    public ResponseEntity<ApiResponse<Void>> createCategory(@RequestBody CategoryCreateRequestDTO req) {
        service.createCategory(
                req.name(),
                req.displayOrder());

        return ResponseEntity.ok(ApiResponse.success(SuccessCode.MENU_CATEGORY_CREAT_SUCCESS, null));
    }
}
