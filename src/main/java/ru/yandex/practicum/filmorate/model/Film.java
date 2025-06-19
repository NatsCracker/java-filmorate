package ru.yandex.practicum.filmorate.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.*;
import ru.yandex.practicum.filmorate.annotation.ReleaseDateConstraint;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter // Получение полей
@Setter // Установка полей
public class Film {
    public Long id;

    @NotBlank(message = "Название не может быть пустым!") // Проверка на пустоту
    private String name;

    @Size (max = 200, message = "Описание не может быть больше 200 символов!") // Проверка на длину
    private String description;

    @NotNull(message = "Дата выхода не может быть пустой!") // Проверка на пустоту
    @ReleaseDateConstraint // Проверка на корректность даты
    private LocalDate releaseDate;

    @Positive(message = "Длительность не может быть отрицательной!") // Проверка на положительность
    private int duration;

    private final Set<Long> likeUsers = new HashSet<>();  // Список пользователей, которым понравился фильм
}
