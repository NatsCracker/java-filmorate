package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.List;

@Slf4j // Логирование
@RestController // Контроллер для REST API
@RequestMapping("/films") // Маршрут для обработки запросов /films
public class FilmController {

    private final List<Film> films = new ArrayList<>();

    @PostMapping // Метод POST для добавления фильма
    public Film addFilm(@Valid @RequestBody Film film) {
        films.add(film);
        return film;
    }

    @PutMapping // Метод PUT для обновления фильма
    public Film updateFilm(@RequestBody Film film) {
        boolean removed = films.removeIf(f -> f.getId() == film.getId());
        if (!removed) {
            throw new RuntimeException("Film with id=" + film.getId() + " not found for update");
        }
        films.add(film);
        return film;
    }

    @GetMapping // Метод GET для получения списка фильмов
    public List<Film> getAllFilms() {
        return films;
    }
}