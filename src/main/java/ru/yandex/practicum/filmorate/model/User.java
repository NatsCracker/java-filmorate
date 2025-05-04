package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class User {
    public Long id;

    @NotBlank(message = "Email не может быть пустым") // Проверка на пустоту
    @Email(message = "Email должен быть валидным") // Проверка на валидность
    public String email;

    @NotBlank(message = "Логин не может быть пустым") // Проверка на пустоту
    @Pattern(regexp = "^\\S+$", message = "Логин не должен содержать пробелы") // Проверка на пробелы
    public String login;

    public String name;

    @PastOrPresent(message = "Дата рождения не может быть в будущем") // Проверка даты рождения на будущее
    public LocalDate birthday;
}
