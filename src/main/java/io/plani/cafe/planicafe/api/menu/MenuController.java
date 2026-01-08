package io.plani.cafe.planicafe.api.menu;

import io.plani.cafe.planicafe.api.menu.dto.CategoryCreateRequestDTO;
import io.plani.cafe.planicafe.api.menu.dto.MenuCreateRequestDTO;
import io.plani.cafe.planicafe.api.menu.dto.MenuDTO;
import io.plani.cafe.planicafe.api.menu.dto.MenuUpdateRequestDTO;
import io.plani.cafe.planicafe.domain.menu.service.MenuService;
import io.plani.cafe.planicafe.global.common.response.ApiResponse;
import io.plani.cafe.planicafe.global.enums.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService service;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createMenu(@RequestBody MenuCreateRequestDTO req) {
        service.createMenu(req);

        return ResponseEntity.ok(ApiResponse.success(SuccessCode.MENU_CREATE_SUCCESS, null));
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

    @PostMapping("/category")
    public ResponseEntity<ApiResponse<Void>> createCategory(@RequestBody CategoryCreateRequestDTO req) {
        service.createCategory(req);

        return ResponseEntity.ok(ApiResponse.success(SuccessCode.MENU_CATEGORY_CREAT_SUCCESS, null));
    }
}
