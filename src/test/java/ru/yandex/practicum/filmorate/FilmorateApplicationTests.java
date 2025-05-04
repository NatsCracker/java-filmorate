package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest // Тесты Spring Boot
@AutoConfigureMockMvc // Автоматическая настройка MockMv
class FilmorateApplicationTests {

	@Autowired // Автоматическая внедрение зависимостей
	private MockMvc mockMvc;

	// Тест для проверки валидации email
	@Test
	void shouldReturnBadRequestWhenEmailIsInvalid() throws Exception {
		String invalidUserJson = """
			{
			"email": "invalidemail.com",
			"login": "validLogin",
			"birthday": "2000-01-01"
			}
			""";

		mockMvc.perform(post("/users")
						.contentType(MediaType.APPLICATION_JSON)
						.content(invalidUserJson))
				.andExpect(status().isBadRequest());
	}

	// Тест для проверки валидации логина
	@Test
	void shouldReturnOkWhenUserIsValid() throws Exception {
		String validUserJson = """
            {
            "email": "user@example.com",
              "login": "validLogin",
              "birthday": "2000-01-01"
              }
            """;

		mockMvc.perform(post("/users")
						.contentType(MediaType.APPLICATION_JSON)
						.content(validUserJson))
				.andExpect(status().isOk());
	}

	// Тест для проверки валидации названия фильма
	@Test
	void shouldReturnBadRequestWhenNameIsEmpty() throws Exception {
		String invalidFilmJson = """
              {
              "name": "",
              "description": "Описание фильма",
              "releaseDate": "2000-01-01",
              "duration": 120
              }
              """;

		mockMvc.perform(post("/films")
						.contentType(MediaType.APPLICATION_JSON)
						.content(invalidFilmJson))
				.andExpect(status().isBadRequest());
	}

	// Тест для проверки валидации длины описания
	@Test
	void shouldReturnBadRequestWhenDescriptionTooLong() throws Exception {
		String longDescription = "a".repeat(201);
		String invalidFilmJson = String.format("""
              {
              "name": "Film Name",
              "description": "%s",
              "releaseDate": "2000-01-01",
              "duration": 100
              }
              """, longDescription);

		mockMvc.perform(post("/films")
						.contentType(MediaType.APPLICATION_JSON)
						.content(invalidFilmJson))
				.andExpect(status().isBadRequest());
	}

	// Тест для проверки валидации даты выхода
	@Test
	void shouldReturnBadRequestWhenReleaseDateTooEarly() throws Exception {
		String invalidFilmJson = """
              {
              "name": "Early Film",
              "description": "Valid",
              "releaseDate": "1800-01-01",
              "duration": 90
              }
              """;

		mockMvc.perform(post("/films")
						.contentType(MediaType.APPLICATION_JSON)
						.content(invalidFilmJson))
				.andExpect(status().isBadRequest());
	}

	// Тест для проверки валидации длительности
	@Test
	void shouldReturnBadRequestWhenDurationIsNegative() throws Exception {
		String invalidFilmJson = """
              {
              "name": "Negative Duration",
              "description": "Valid",
              "releaseDate": "2000-01-01",
              "duration": -90
              }
              """;

		mockMvc.perform(post("/films")
						.contentType(MediaType.APPLICATION_JSON)
						.content(invalidFilmJson))
				.andExpect(status().isBadRequest());
	}

	// Тест для проверки валидации фильма
	@Test
	void shouldReturnOkWhenFilmIsValid() throws Exception {
		String validFilmJson = """
              {
              "name": "Good Film",
              "description": "Short description",
              "releaseDate": "2000-01-01",
              "duration": 100
              }
              """;

		mockMvc.perform(post("/films")
						.contentType(MediaType.APPLICATION_JSON)
						.content(validFilmJson))
				.andExpect(status().isOk());
	}

}