package ru.kerchik.linkShortener.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kerchik.linkShortener.dto.common.CommonResponse;
import ru.kerchik.linkShortener.dto.common.ValidationError;
import ru.kerchik.linkShortener.exception.NotFoundPageException;

import java.util.List;


@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class LinkShortenerExceptionHandler {

    private final String notFoundPage;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResponse<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        BindingResult bindingResult = e.getBindingResult();

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        List<ValidationError> validationErrors = fieldErrors.stream()
                .map(fieldError -> ValidationError.builder()
                        .field(fieldError.getField())
                        .message(fieldError.getDefaultMessage())
                        .build()
                ).toList();

        log.error("Ошибка валидации запроса: {}", validationErrors);

        return CommonResponse.builder()
                .errorMessage("Ошибка валидации")
                .validationErrors(validationErrors)
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public CommonResponse<?> handleException(Exception e) {
        log.error("Непредвиденная ошибка: {}", e.getMessage(), e);

        return CommonResponse.builder()
                .errorMessage("Непредвиденная ошибка: " + e.getMessage())
                .build();
    }

    @ExceptionHandler(NotFoundPageException.class)
    public ResponseEntity<String> handleNotFoundPageException(NotFoundPageException e) {
        log.error(e.getMessage(), e);

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.TEXT_HTML)
                .body(notFoundPage);
    }
}
