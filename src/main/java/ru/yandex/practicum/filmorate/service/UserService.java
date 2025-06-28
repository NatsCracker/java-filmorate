package ru.yandex.practicum.filmorate.service;

import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserStorage userStorage;

    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    // Добавить пользователя в хранилище
    public User add(User user) {
        return userStorage.createUser(user);
    }

    // Обновить пользователя в хранилище
    public User update(User user) {
        return userStorage.updateUser(user);
    }

    // Получить всех пользователей из хранилища
    public List<User> getAll() {
        return userStorage.getAllUsers();
    }

    // Получить пользователя по id
    public User getById(long userId) {
        return getUser(userId);
    }

    // Добавить друга
    public void addFriend(long userId, long friendId) {
        if (userId == friendId) {
            throw new ValidationException("Нельзя добавить самого себя в друзья.");
        }

        User user = Optional.ofNullable(userStorage.getUserById(userId)).orElseThrow(() -> new NotFoundException("Пользователь с id=%d не найден".formatted(userId)));

        User friend = Optional.ofNullable(userStorage.getUserById(friendId)).orElseThrow(() -> new NotFoundException("Пользователь с id=%d не найден".formatted(friendId)));

        user.getListFriends().add(friendId);
        friend.getListFriends().add(userId);

    }

    // Удалить друга
    public void removeFriend(long userId, long friendId) {
        if (userId == friendId) {
            throw new ValidationException("Нельзя удалить самого себя из друзей.");
        }

        User user = Optional.ofNullable(userStorage.getUserById(userId)).orElseThrow(() -> new NotFoundException("Пользователь с id=%d не найден".formatted(userId)));

        User friend = Optional.ofNullable(userStorage.getUserById(friendId)).orElseThrow(() -> new NotFoundException("Пользователь с id=%d не найден".formatted(friendId)));

        user.getListFriends().remove(friendId);
        friend.getListFriends().remove(userId);
    }

    // Получить список друзей
    public List<User> getFriends(long userId) {
        return getUser(userId).getListFriends().stream().map(this::getUser).collect(Collectors.toList());
    }

    // Получить список общих друзей
    public List<User> getCommonFriends(long userId1, long userId2) {
        Set<Long> first = getUser(userId1).getListFriends();
        Set<Long> second = getUser(userId2).getListFriends();

        return first.stream().filter(second::contains).map(this::getUser).collect(Collectors.toList());
    }

    // Получить пользователя по id из хранилища если он существует
    private User getUser(long userId) {
        return Optional.ofNullable(userStorage.getUserById(userId)).orElseThrow(() -> new NotFoundException("Пользователь с id=" + userId + " не найден"));
    }
}
