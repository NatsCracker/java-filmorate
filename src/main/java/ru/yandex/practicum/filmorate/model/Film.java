package ru.yandex.practicum.filmorate.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.*;
import ru.yandex.practicum.filmorate.annotation.ReleaseDateConstraint;

import java.time.LocalDate;

/**
 * Film.
 */
@Getter // Получение полей
@Setter // Установка полей
public class Film {
    public Long id;

    @NotBlank(message = "Название не может быть пустым!") // Проверка на пустоту
    public String name;

    @Size (max = 200, message = "Описание не может быть больше 200 символов!") // Проверка на длину
    public String description;

    @NotNull(message = "Дата выхода не может быть пустой!") // Проверка на пустоту
    @ReleaseDateConstraint // Проверка на корректность даты
    public LocalDate releaseDate;

    @Positive(message = "Длительность не может быть отрицательной!") // Проверка на положительность
    public int duration;
}