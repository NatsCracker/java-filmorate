package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;


@Slf4j // Логирование
@RestController // Контроллер для REST API
@RequestMapping("/users") // Маршрут для обработки запросов по адресу /users
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping // Метод POST для создания пользователя
    public User createUser(@Valid @RequestBody User user) {
        return userService.add(user);
    }

    @PutMapping // Метод PUT для обновления пользователя
    public User updateUser(@Valid @RequestBody User user) {
        return userService.update(user);
    }

    @GetMapping("/{userId}") // Метод GET для получения пользователя по ID
    public User getUserById(@PathVariable Long userId) {
        return userService.getById(userId);
    }

    @GetMapping // Метод GET для получения всех пользователей
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    @PutMapping("/{userId}/friends/{friendId}")  // Метод PUT для добавления друга
    public void addFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        userService.addFriend(userId, friendId);
    }

    @DeleteMapping("/{userId}/friends/{friendId}") // Метод DELETE для удаления друга
    public void removeFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        userService.removeFriend(userId, friendId);
    }

    @GetMapping("/{userId}/friends") // Метод GET для получения списка друзей
    public List<User> getFriends(@PathVariable Long userId) {
        return userService.getFriends(userId);
    }

    @GetMapping("/{userId}/friends/common/{otherId}") // Метод GET для получения общих друзей
    public List<User> getCommonFriends(@PathVariable Long userId, @PathVariable Long otherId) {
        return userService.getCommonFriends(userId, otherId);
    }
}