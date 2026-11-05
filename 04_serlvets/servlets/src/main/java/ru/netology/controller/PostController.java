package ru.netology.controller;

import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/posts")
public class PostController {
    public static final String APPLICATION_JSON = "application/json";
    private final PostService service;
    private final Gson gson;

    public PostController(PostService service) {
        this.service = service;
        this.gson = new Gson();
    }

    @GetMapping
    public void all(HttpServletResponse response) {
        response.setContentType(APPLICATION_JSON);
        final var data = service.all();
//        final var gson = new Gson();
        try {
            response.getWriter().print(gson.toJson(data));
        } catch (IOException e) {
            System.err.println("Здесь будут логи с использованием SLF4J...позже");
        }
    }

    @GetMapping("/{id}")
    public void getById(@PathVariable long id, HttpServletResponse response) {
        response.setContentType(APPLICATION_JSON);
        response.setCharacterEncoding("UTF-8");
        Post post = null;
        try {
            try {
                post = service.getById(id);
            } catch (NotFoundException nfe) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                Map<String, Object> error = new HashMap<>();
                error.put("error", true);
                error.put("message", String.format("Ошибка получения Post по id: %d. Идентификатор отсутсвует ", id));
                response.getWriter().write(gson.toJson(error));
                return;
            }
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(gson.toJson(post));
        } catch (IOException e) {
            System.err.println("Ошибка отправки ответа клиенту");
        }
    }

    @PostMapping
    public void save(Reader body, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        final var post = gson.fromJson(body, Post.class);
        final var data = service.save(post);
        if (data.isPresent()) {
            response.getWriter().print(gson.toJson(data));
            return;
        }
        response.sendRedirect("/api/posts");
    }

    @DeleteMapping("/{id}")
    public void removeById(@PathVariable long id, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType(APPLICATION_JSON);
        try {
            if (service.removeById(id)) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(gson.toJson(service.all()));
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                Map<String, Object> error = new HashMap<>();
                error.put("error", true);
                error.put("message", String.format("Ошибка удаления id: %d. Идентификатор отсутсвует ", id));
                response.getWriter().write(gson.toJson(error));
            }
        } catch (IOException e) {
            System.err.println("Здесь будет лог slf4j");
        }
    }

}
