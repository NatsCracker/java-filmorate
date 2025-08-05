package ru.yandex.practicum.filmorate.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    /* ---------- 400 BAD REQUEST ---------- */
    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class, jakarta.validation.ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidation(Exception e) {
        log.warn("Ошибка валидации: {}", e.getMessage());
        return new ErrorResponse(e.getMessage());
    }

    /* ---------- 404 NOT FOUND ---------- */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(NotFoundException e) {
        log.warn("Ресурс не найден: {}", e.getMessage());
        return new ErrorResponse(e.getMessage());
    }

    /* ---------- 500 INTERNAL SERVER ERROR ---------- */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleThrowable(Throwable e) {
        log.error("Непредвиденная ошибка: {}", e.getMessage(), e);
        return new ErrorResponse("Произошла непредвиденная ошибка.");
    }
}