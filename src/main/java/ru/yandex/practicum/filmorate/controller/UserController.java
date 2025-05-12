package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j // Логирование
@RestController // Контроллер для REST API
@RequestMapping("/users") // Маршрут для обработки запросов по адресу /users
public class UserController {

    private final Map<Long, User> users = new HashMap<>();
    private long nextId = 1L;

    @PostMapping // Метод POST для создания пользователя
    public User createUser(@Valid @RequestBody User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setId(nextId++);
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping // Метод PUT для обновления пользователя
    public User updateUser(@Valid @RequestBody User user) {
        if(!users.containsKey(user.getId())){
            throw new NotFoundException("User with ID " + user.getId() + " not found");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        users.put(user.getId(), user);
        return user;
    }

    @GetMapping // Метод GET для получения всех пользователей
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }
}