package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.List;

@Slf4j // Логирование
@RestController // Контроллер для REST API
@RequestMapping("/films") // Маршрут для обработки запросов /films
public class FilmController {
    private final FilmService filmService;

    public FilmController(FilmService filmService, FilmStorage filmStorage) {
        this.filmService = filmService;
    }

    @PostMapping // Метод POST для добавления фильма
    public Film addFilm(@Valid @RequestBody Film film) {
        return filmService.addFilm(film);
    }

    @PutMapping // Метод PUT для обновления фильма
    public Film updateFilm(@RequestBody Film film) {
        return filmService.updateFilm(film);
    }

    @GetMapping("/{filmId}") // Метод GET для получения фильма по ID
    public Film getFilmById(@PathVariable long filmId) {
        return filmService.getById(filmId);
    }

    @GetMapping // Метод GET для получения списка фильмов
    public List<Film> getAllFilms() {
        return filmService.getAllFilms();
    }

    @PutMapping("/{filmId}/like/{userId}") // Метод PUT для добавления лайка
    public void addLike(@PathVariable long filmId, @PathVariable long userId) {
        filmService.addLike(filmId, userId);
    }

    @DeleteMapping("/{filmId}/like/{userId}") // Метод DELETE для удаления лайка
    public void removeLike(@PathVariable long filmId, @PathVariable long userId) {
        filmService.removeLike(filmId, userId);
    }

    @GetMapping("/popular") // Метод GET для получения списка популярных фильмов
    public List<Film> getPopularFilms(@RequestParam(defaultValue = "10") int count) {
        return filmService.getMostPopular(count);
    }
}