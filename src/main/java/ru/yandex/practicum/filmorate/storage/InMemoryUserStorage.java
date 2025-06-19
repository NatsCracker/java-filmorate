package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();
    private long nextId = 1L;

    // Добавление пользователя в хранилище
    @Override
    public User createUser(User user) {
        normalizaName(user);
        user.setId(nextId++);
        users.put(user.getId(), user);
        return user;
    }

    // Обновление пользователя в хранилище
    @Override
    public User updateUser(User user) {
        if (!users.containsKey(user.getId())) {
            throw new NotFoundException("Пользователь с ID=" + user.getId() + " не найден");
        }
        normalizaName(user);
        users.put(user.getId(), user);
        return user;
    }

    // Удаление пользователя из хранилища
    @Override
    public void deleteUser(long id) {
        if (!users.containsKey(id)) {
            throw new NotFoundException("Пользователь с ID=" + id + " не найден");
        }
        users.remove(id);
    }

    // Получение пользователя по ID
    @Override
    public Optional<User> getUserById(long id) {
        if (!users.containsKey(id)) {
            throw new NotFoundException("Пользователь с ID=" + id + " не найден");
        }
        return Optional.ofNullable(users.get(id));
    }

    // Получение всех пользователей
    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    // Нормализация имени пользователя
    private void normalizaName(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
