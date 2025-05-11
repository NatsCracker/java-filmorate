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
    private long nextId = 1L;

    @PostMapping // Метод POST для создания пользователя
    public User createUser(@Valid @RequestBody User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setId(nextId++);
        users.add(user);
        return user;
    }

    @PutMapping // Метод PUT для обновления пользователя
    public User updateUser(@Valid @RequestBody User user) {
        boolean removed = users.removeIf(u -> u.getId() == user.getId());
        if (!removed) {
            throw new RuntimeException("User with id=" + user.getId() + " not found for update");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        users.add(user);
        return user;
    }

    @GetMapping // Метод GET для получения всех пользователей
    public List<User> getAllUsers() {
        return users;
    }
}