package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.List;

@Slf4j // Логирование
@RestController // Контроллер для REST API
@RequestMapping("/users") // Маршрут для обработки запросов по адресу /users
public class UserController {

    private final List<User> users = new ArrayList<>();

    @PostMapping // Метод POST для создания пользователя
    public User createUser(@Valid @RequestBody User user) {
        users.add(user);
        return user;
    }

    @PutMapping // Метод PUT для обновления пользователя
    public User updateUser(@RequestBody User user) {
        users.removeIf(u -> u.getId() == user.getId());
        users.add(user);
        return user;
    }

    @GetMapping // Метод GET для получения всех пользователей
    public List<User> getAllUsers() {
        return users;
    }
}
