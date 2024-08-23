package ru.academits.java.kononov.phonebookspringboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class BaseResponse {
    private final boolean success;
    private String message;

    public static BaseResponse createSuccessResponse() {
        return new BaseResponse(true);
    }

    public static BaseResponse createErrorResponse(String message) {
        return new BaseResponse(false, message);
    }
}
