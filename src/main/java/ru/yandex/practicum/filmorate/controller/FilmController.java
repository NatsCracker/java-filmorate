package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.exception.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j // Логирование
@RestController // Контроллер для REST API
@RequestMapping("/films") // Маршрут для обработки запросов /films
public class FilmController {

    private final Map<Long, Film> films = new HashMap<>();
    private long nextId = 1;

    @PostMapping // Метод POST для добавления фильма
    public Film addFilm(@Valid @RequestBody Film film) {
        film.setId(nextId++);
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping // Метод PUT для обновления фильма
    public Film updateFilm(@RequestBody Film film) {
        if (!films.containsKey(film.getId())) {
            throw new NotFoundException("Film with id=" + film.getId() + " not found");
        }
        films.put(film.getId(), film);
        return film;
    }

    @GetMapping // Метод GET для получения списка фильмов
    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }
}