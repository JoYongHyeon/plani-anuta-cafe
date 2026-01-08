package io.plani.cafe.planicafe.domain.menu.exception;

import io.plani.cafe.planicafe.global.enums.ErrorCode;
import lombok.Getter;

@Getter
public class MenuException extends RuntimeException {
    private ErrorCode errorCode;

    public MenuException(ErrorCode errorCode) {
        super(errorCode.getMessage());  // cause 전달
        this.errorCode = errorCode;
    }
}