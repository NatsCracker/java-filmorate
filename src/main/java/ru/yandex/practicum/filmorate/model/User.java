package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {
    public Long id;

    @NotBlank(message = "Email не может быть пустым") // Проверка на пустоту
    @Email(message = "Email должен быть валидным") // Проверка на валидность
    private String email;

    @NotBlank(message = "Логин не может быть пустым") // Проверка на пустоту
    @Pattern(regexp = "^\\S+$", message = "Логин не должен содержать пробелы") // Проверка на пробелы
    private String login;

    private String name;

    @PastOrPresent(message = "Дата рождения не может быть в будущем") // Проверка даты рождения на будущее
    private LocalDate birthday;

    private Set<Long> listFriends = new HashSet<>(); // Список друзей


    // Добавить друга
    public void addFriend(long friendId) {
        if (friendId != id) {
            if (!listFriends.contains(friendId)) {
                listFriends.add(friendId);
            }
        }
    }

    // Удалить друга
    public void removeFriend(long friendId) {
        listFriends.remove(friendId);
    }
}