package io.plani.cafe.planicafe.global.exception;

import io.plani.cafe.planicafe.domain.menu.exception.MenuException;
import io.plani.cafe.planicafe.global.common.response.ApiResponse;
import io.plani.cafe.planicafe.global.enums.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 비즈니스 로직 중 발생하는 모든 예외를 ApiResponse 규격으로 변환
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Void>> handleRuntimeException(RuntimeException e) {
        log.error("[비즈니스 로직 에러 발생: {}]", e.getMessage());

        return ResponseEntity
                .status(500)
                .body(ApiResponse.failure(ErrorCode.INTERNAL_SERVER_ERROR));
    }

    /**
     * 토스 결제 실패 등 특정 상황에 대한 세밀한 에러 처리
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadRequest(IllegalArgumentException e) {
        return ResponseEntity
                .status(400)
                .body(ApiResponse.error(ErrorCode.INVALID_INPUT_VALUE, e.getMessage()));
    }

    /**
     * 메뉴 도메인 예외 처리
     */
    @ExceptionHandler(MenuException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(MenuException e) {
        log.error("[메뉴 예외 발생] code={}, message={}", e.getErrorCode(), e.getMessage());

        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(ApiResponse.failure(e.getErrorCode()));
    }
}
