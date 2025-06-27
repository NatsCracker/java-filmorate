package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private static final int DEFAULT_COUNT = 10;

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    // Добавление фильма в хранилище
    public Film addFilm(Film film) {
        return filmStorage.addFilm(film);
    }

    // Обновление фильма в хранилище
    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }

    // Получение фильма по id
    public Film getById(long filmId) {
        return getFilm(filmId);
    }

    // Получение всех фильмов
    public List<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    // Добавление лайка к фильму
    public void addLike(long filmId, long userId) {
        Film film = getFilm(filmId);
        film.addLike(userId);
    }

    // Удаление лайка к фильму
    public void removeLike(long filmId, long userId) {
        Film film = getFilm(filmId);
        film.removeLike(userId);
    }

    // Получение самых популярных фильмов
    public List<Film> getMostPopular(int count) {
        int limit = count > 0 ? count : DEFAULT_COUNT;

        return filmStorage.getAllFilms().stream().sorted(Comparator.comparingInt((Film f) -> f.getLikeUsers().size()).reversed()).limit(limit).collect(Collectors.toList());
    }

    // Получение фильмов пользователя
    private Film getFilm(long filmId) {
        return filmStorage.getFilmById(filmId).orElseThrow(() -> new NotFoundException("Фильм с id=%d не найден".formatted(filmId)));
    }

    // Проверка существования пользователя
    private void ensureUserExists(long userId) {
        userStorage.getUserById(userId).orElseThrow(() -> new NotFoundException("Пользователь с id=%d не найден".formatted(userId)));
    }
}
