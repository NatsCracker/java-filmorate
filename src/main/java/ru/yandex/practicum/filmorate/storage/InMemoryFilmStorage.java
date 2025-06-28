package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> films = new HashMap<>();
    private long nextId = 1;

    // Добавление фильма в хранилище
    @Override
    public Film addFilm(Film film) {
        film.setId(nextId++);
        films.put(film.getId(), film);
        return film;
    }

    // Обновление фильма в хранилище
    @Override
    public Film updateFilm(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new NotFoundException("Фильм с id=" + film.getId() + " не найден");
        }
        films.put(film.getId(), film);
        return film;
    }

    // Удаление фильма из хранилища
    @Override
    public void deleteFilm(long id) {
        if (films.remove(id) == null) {
            throw new NotFoundException("Фильм с id=" + id + " не найден");
        }
    }

    // Получение всех фильмов из хранилища
    @Override
    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }

    // Получение фильма по id
    @Override
    public Film getFilmById(long id) {
        return Optional.ofNullable(films.get(id))
                .orElseThrow(() -> new NotFoundException("Фильм с id=%d не найден".formatted(id)));
    }
}
